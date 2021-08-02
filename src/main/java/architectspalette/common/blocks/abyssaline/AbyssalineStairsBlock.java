package architectspalette.common.blocks.abyssaline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;


public class AbyssalineStairsBlock extends StairsBlock implements IAbyssalineChargeable {

	public AbyssalineStairsBlock(Supplier<BlockState> state, Properties properties) {
		super(state, properties);
		this.setDefaultState(this.getStateContainer().getBaseState().with(NewAbyssalineBlock.CHARGED, false));
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return this.isCharged(state) ? AbyssalineHelper.CHARGE_LIGHT : 0;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(NewAbyssalineBlock.CHARGED, StairsBlock.SHAPE, StairsBlock.FACING, StairsBlock.HALF, StairsBlock.WATERLOGGED);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return super.updatePostPlacement(state, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		AbyssalineHelper.abyssalineTick(state, worldIn, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 1);
		return super.getStateForPlacement(context);
	}

	//Interface things
	@Override
	public boolean outputsChargeFrom(BlockState stateIn, Direction faceIn) {
		boolean topface = (stateIn.get(StairsBlock.HALF) == Half.TOP);
		boolean directionmatches = (topface && faceIn == Direction.UP) || (!topface && faceIn == Direction.DOWN);
		return this.isCharged(stateIn) && directionmatches;
	}

	@Override
	public boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
		return faceIn == stateIn.get(StairsBlock.FACING);
	}

	@Override
	public Direction getSourceDirection(BlockState stateIn) {
		return stateIn.get(StairsBlock.FACING);
	}

	@Override
	// The stairs shouldn't rotate just to recieve charge.
	public BlockState getStateWithChargeDirection(BlockState stateIn, Direction faceOut) {
		return stateIn;
	}
}