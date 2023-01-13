package architectspalette.content.blocks.abyssaline;

import architectspalette.content.blocks.VerticalSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGED;
import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGE_SOURCE;

public class AbyssalineVerticalSlabBlock extends VerticalSlabBlock implements IAbyssalineChargeable {

	public AbyssalineVerticalSlabBlock(Properties props) {
		super(props);
		this.registerDefaultState(this.defaultBlockState().setValue(CHARGE_SOURCE, Direction.NORTH).setValue(CHARGED, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(CHARGED, CHARGE_SOURCE, TYPE, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return AbyssalineHelper.getStateWithNeighborCharge(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
	}

	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
	}
	
	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		AbyssalineHelper.abyssalineTick(state, worldIn, pos);
	}

	//Interface stuff
	@Override
	public boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
		VerticalSlabType type = stateIn.getValue(TYPE);
		return type == VerticalSlabType.DOUBLE || faceIn != type.direction;
	}

	@Override
	public boolean outputsChargeTo(BlockState stateIn, Direction faceIn) {
		return IAbyssalineChargeable.super.outputsChargeTo(stateIn, faceIn) && this.acceptsChargeFrom(stateIn, faceIn);
	}

	// Slabs should never transfer power through the faces that don't collide, so don't provide a state here that can.
	@Override
	public BlockState getStateWithChargeDirection(BlockState stateIn, Direction directionToSource) {
		VerticalSlabType type = stateIn.getValue(TYPE);
		if(type.direction == directionToSource)
			return stateIn;
		
		return stateIn.setValue(CHARGE_SOURCE, directionToSource);
	}

}
