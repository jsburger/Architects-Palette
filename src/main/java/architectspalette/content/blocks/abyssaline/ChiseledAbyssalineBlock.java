package architectspalette.content.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGED;

public class ChiseledAbyssalineBlock extends Block implements IAbyssalineChargeable {
	public final static Item KEY = Items.HEART_OF_THE_SEA;
	private final static BlockPos OFFSET = new BlockPos(0, 0, 0);

	//Abyssaline stuff
	public boolean outputsChargeFrom(BlockState stateIn, Direction faceIn) {
		return stateIn.getValue(CHARGED);
	}

	public boolean isCharged(BlockState stateIn) {
		return stateIn.getValue(CHARGED);
	}

	public BlockPos getSourceOffset(BlockState stateIn) {
		return OFFSET;
	}

	//Normal Block stuff
	public ChiseledAbyssalineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(CHARGED, false));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState();
	}
	
	public static int getLightValue(BlockState state) {
		return state.getValue(CHARGED) ? 14 : 0;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(CHARGED);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult traceResult) {
		ItemStack stack = player.getItemInHand(hand);
		if (!this.isCharged(state) && stack.getItem() == KEY) {
			if(!player.isCreative())
				stack.shrink(1);
			world.setBlockAndUpdate(pos, this.getStateWithCharge(state, true));
			world.playSound(null, pos, SoundEvents.CONDUIT_ACTIVATE, SoundSource.BLOCKS, 0.5F, new Random().nextFloat() * 0.2F + 0.8F);
			return InteractionResult.SUCCESS;
		}
		else if (this.isCharged(state) && stack.isEmpty()) {
			world.setBlockAndUpdate(pos, this.getStateWithCharge(state, false));
			world.playSound(null, pos, SoundEvents.CONDUIT_DEACTIVATE, SoundSource.BLOCKS, 0.5F, new Random().nextFloat() * 0.2F + 0.8F);
			if(!player.isCreative() || (player.getInventory().countItem(KEY) <= 0))
				player.addItem(new ItemStack(KEY));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}