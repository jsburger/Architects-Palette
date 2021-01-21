package architectspalette.common.blocks.abyssaline;

import architectspalette.common.tileentity.ChiseledAbyssalineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGED;

public class ChiseledAbyssalineBlock extends Block implements IAbyssalineChargeable {
	public final static Item KEY = Items.HEART_OF_THE_SEA;
	private final static BlockPos OFFSET = new BlockPos(0, 0, 0);

	public boolean outputsChargeFrom(BlockState stateIn, Direction faceIn) {
		return stateIn.get(CHARGED);
	}

	public boolean isCharged(BlockState stateIn) {
		return stateIn.get(CHARGED);
	}

	public BlockPos getSourceOffset(BlockState stateIn) {
		return OFFSET;
	}

	public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 20);

	public ChiseledAbyssalineBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(CHARGED, false));
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return state;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState();
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return this.isCharged(state) ? 14 : 0;
//		return this.isCharged(state) ? (state.get(LIGHT) / 2) + 4 : 0;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(LIGHT, CHARGED);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ChiseledAbyssalineTileEntity();
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