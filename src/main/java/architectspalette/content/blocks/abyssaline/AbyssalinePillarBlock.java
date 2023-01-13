package architectspalette.content.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGED;
import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGE_SOURCE;

public class AbyssalinePillarBlock extends RotatedPillarBlock implements IAbyssalineChargeable {
//	public static final EnumProperty<PillarSide> CHARGE_SIDE = EnumProperty.create("charge_side", PillarSide.class);

	public AbyssalinePillarBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CHARGE_SOURCE, Direction.NORTH).setValue(CHARGED, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(AXIS, CHARGED, CHARGE_SOURCE);
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
		return faceIn.getAxis() == stateIn.getValue(AXIS);
	}

	@Override
	public boolean outputsChargeTo(BlockState stateIn, Direction faceIn) {
		return this.isCharged(stateIn) &&
				!(faceIn == this.getSourceDirection(stateIn)) &&
				faceIn.getAxis() == stateIn.getValue(AXIS);

//		return this.isCharged(stateIn) &&
//				!(faceIn.getAxisDirection().getOffset() == stateIn.get(CHARGE_SIDE).toScalar()) &&
//				faceIn.getAxis() == stateIn.get(AXIS);
	}

	@Override
	public boolean pushesPower(BlockState stateIn) {
		return true;
	}

	@Override
	public boolean pullsPowerFrom(BlockState stateIn, Direction faceIn) {
		return faceIn.getAxis() == stateIn.getValue(AXIS);
	}

//	@Override
//	public Direction getSourceDirection(BlockState stateIn) {
//		return directionFromAxis(stateIn.get(AXIS), stateIn.get(CHARGE_SIDE).toScalar());
//	}

//	@Override
//	public BlockState getStateWithChargeDirection(BlockState stateIn, Direction faceOut) {
//		if (faceOut.getAxis() == stateIn.get(AXIS)) {
//			return stateIn.with(CHARGE_SIDE, fromScalar(faceOut.getAxisDirection().getOffset()));
//		}
//		return stateIn;
//	}

	private static Direction directionFromAxis(Direction.Axis axis, Integer i) {
		switch(axis) {
			case X:
				return i > 0 ? Direction.EAST : Direction.WEST;
			case Y:
				return i > 0 ? Direction.UP : Direction.DOWN;
			case Z:
				return i > 0 ? Direction.SOUTH : Direction.NORTH;
		}
		return Direction.UP;
	}

	private PillarSide fromScalar(int integer) {
		return integer > 0 ? PillarSide.FRONT : PillarSide.BACK;
	}

	private enum PillarSide implements StringRepresentable {
		FRONT,
		BACK;

		public int toScalar() {
			return this == FRONT ? 1 : -1;
		}

		public String toString() { return this.getSerializedName(); }

		public String getSerializedName() {
			return this == FRONT ? "front" : "back";
		}
	}

}