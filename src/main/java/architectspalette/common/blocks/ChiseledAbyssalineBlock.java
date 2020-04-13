package architectspalette.common.blocks;

import java.util.Random;

import architectspalette.common.tileentity.ChiseledAbyssalineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ChiseledAbyssalineBlock extends AbyssalineBlock {
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
		int light = state.get(LIGHT) / 2;
		return light;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(LIGHT, CHARGED);
	}
	
	@Override
	public boolean isEmissiveRendering(BlockState state) {
		return state.get(LIGHT) / 2 > 0;
	}
	
	@Override
	public boolean needsPostProcessing(BlockState state, IBlockReader worldIn, BlockPos pos) {
		int light = state.get(LIGHT) / 2;
		return light < 6;
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
		if(!state.get(CHARGED) && stack.getItem() == Items.HEART_OF_THE_SEA) {
			if(!player.isCreative()) stack.shrink(1);
			
			world.setBlockState(pos, state.with(CHARGED, true));
			world.playSound(null, pos, SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.BLOCKS, 0.5F, new Random().nextFloat() * 0.2F + 0.8F);
			return ActionResultType.CONSUME;
		} else if(state.get(CHARGED) && stack.isEmpty()) {
			world.setBlockState(pos, state.with(CHARGED, false));
			world.playSound(null, pos, SoundEvents.BLOCK_CONDUIT_DEACTIVATE, SoundCategory.BLOCKS, 0.5F, new Random().nextFloat() * 0.2F + 0.8F);
			player.addItemStackToInventory(new ItemStack(Items.HEART_OF_THE_SEA));
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
}