package architectspalette.common.blocks.abyssaline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGED;
import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGE_SOURCE;

public class AbyssalinePillarBlock extends RotatedPillarBlock implements IAbyssalineChargeable {
//	public static final EnumProperty<PillarSide> CHARGE_SIDE = EnumProperty.create("charge_side", PillarSide.class);

	public AbyssalinePillarBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(CHARGE_SOURCE, Direction.NORTH).with(CHARGED, false));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(AXIS, CHARGED, CHARGE_SOURCE);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return this.isCharged(state) ? AbyssalineHelper.CHARGE_LIGHT : 0;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 1);
		return super.getStateForPlacement(context);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		AbyssalineHelper.abyssalineTick(state, worldIn, pos);
	}

	//Interface stuff
	@Override
	public boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
		return faceIn.getAxis() == stateIn.get(AXIS);
	}

	@Override
	public boolean outputsChargeFrom(BlockState stateIn, Direction faceIn) {
		return this.isCharged(stateIn) &&
				!(faceIn == this.getSourceDirection(stateIn)) &&
				faceIn.getAxis() == stateIn.get(AXIS);

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
		return faceIn.getAxis() == stateIn.get(AXIS);
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

	private enum PillarSide implements IStringSerializable {
		FRONT,
		BACK;

		public int toScalar() {
			return this == FRONT ? 1 : -1;
		}

		public String toString() { return this.getString(); }

		public String getString() {
			return this == FRONT ? "front" : "back";
		}
	}

}