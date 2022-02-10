package architectspalette.content.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.Random;

import static architectspalette.content.blocks.abyssaline.NewAbyssalineBlock.CHARGED;
import static architectspalette.content.blocks.abyssaline.NewAbyssalineBlock.CHARGE_SOURCE;

public class AbyssalineSlabBlock extends SlabBlock implements IAbyssalineChargeable {

	public AbyssalineSlabBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CHARGE_SOURCE, Direction.NORTH).setValue(CHARGED, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(CHARGED, CHARGE_SOURCE, TYPE, WATERLOGGED);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		context.getLevel().scheduleTick(context.getClickedPos(), this, 1);
		return super.getStateForPlacement(context);
	}

	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
	}
	
	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		AbyssalineHelper.abyssalineTick(state, worldIn, pos);
	}

	//Interface stuff
	@Override
	public boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
		switch(stateIn.getValue(TYPE)) {
			case TOP: return faceIn != Direction.DOWN;
			case BOTTOM: return faceIn != Direction.UP;
			default: return true;
		}
	}

	@Override
	public boolean outputsChargeFrom(BlockState stateIn, Direction faceIn) {
		return IAbyssalineChargeable.super.outputsChargeFrom(stateIn, faceIn) && this.acceptsChargeFrom(stateIn, faceIn);
	}

	// Slabs should never transfer power through the faces that don't collide, so don't provide a state here that can.
	@Override
	public BlockState getStateWithChargeDirection(BlockState stateIn, Direction faceOut) {
		SlabType type = stateIn.getValue(TYPE);
		if (type == SlabType.TOP && faceOut == Direction.DOWN) return stateIn;
		if (type == SlabType.BOTTOM && faceOut == Direction.UP) return stateIn;
		return stateIn.setValue(CHARGE_SOURCE, faceOut);
	}

}