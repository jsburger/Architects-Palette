package architectspalette.content.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Half;

import java.util.function.Supplier;

public class AbyssalineStairsBlock extends StairBlock implements IAbyssalineChargeable {

	public AbyssalineStairsBlock(Supplier<BlockState> state, Properties properties) {
		super(state, properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(AbyssalineBlock.CHARGED, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(AbyssalineBlock.CHARGED, StairBlock.SHAPE, StairBlock.FACING, StairBlock.HALF, StairBlock.WATERLOGGED);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return super.updateShape(state, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		AbyssalineHelper.abyssalineTick(state, worldIn, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return AbyssalineHelper.getStateWithNeighborCharge(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
	}

	//Interface things
	@Override
	public boolean outputsChargeTo(BlockState stateIn, Direction faceIn) {
		boolean topface = (stateIn.getValue(StairBlock.HALF) == Half.TOP);
		boolean directionmatches = (topface && faceIn == Direction.UP) || (!topface && faceIn == Direction.DOWN);
		return this.isCharged(stateIn) && directionmatches;
	}

	@Override
	public boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
		return faceIn == stateIn.getValue(StairBlock.FACING);
	}

	@Override
	public Direction getSourceDirection(BlockState stateIn) {
		return stateIn.getValue(StairBlock.FACING);
	}

	@Override
	// The stairs shouldn't rotate just to recieve charge.
	public BlockState getStateWithChargeDirection(BlockState stateIn, Direction directionToSource) {
		return stateIn;
	}
}
