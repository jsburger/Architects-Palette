package architectspalette.common.blocks.abyssaline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGED;

public class ChiseledAbyssalineBlock extends Block implements IAbyssalineChargeable {
	public final static Item KEY = Items.HEART_OF_THE_SEA;
	private final static BlockPos OFFSET = new BlockPos(0, 0, 0);

	//Abyssaline stuff
	public boolean outputsChargeFrom(BlockState stateIn, Direction faceIn) {
		return stateIn.get(CHARGED);
	}

	public boolean isCharged(BlockState stateIn) {
		return stateIn.get(CHARGED);
	}

	public BlockPos getSourceOffset(BlockState stateIn) {
		return OFFSET;
	}

	//Normal Block stuff
	public ChiseledAbyssalineBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(CHARGED, false));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState();
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return this.isCharged(state) ? 14 : 0;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(CHARGED);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
		ItemStack stack = player.getHeldItem(hand);
		if (!this.isCharged(state) && stack.getItem() == KEY) {
			if(!player.isCreative())
				stack.shrink(1);
			world.setBlockState(pos, this.getStateWithCharge(state, true));
			world.playSound(null, pos, SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.BLOCKS, 0.5F, new Random().nextFloat() * 0.2F + 0.8F);
			return ActionResultType.CONSUME;
		}
		else if (this.isCharged(state) && stack.isEmpty()) {
			world.setBlockState(pos, this.getStateWithCharge(state, false));
			world.playSound(null, pos, SoundEvents.BLOCK_CONDUIT_DEACTIVATE, SoundCategory.BLOCKS, 0.5F, new Random().nextFloat() * 0.2F + 0.8F);
			if(!player.isCreative() || (player.inventory.count(KEY) <= 0))
				player.addItemStackToInventory(new ItemStack(KEY));
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
}