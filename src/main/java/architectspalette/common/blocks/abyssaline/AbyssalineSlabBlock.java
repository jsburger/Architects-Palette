package architectspalette.common.blocks.abyssaline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGED;
import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGE_SOURCE;

public class AbyssalineSlabBlock extends SlabBlock implements IAbyssalineChargeable {

	public AbyssalineSlabBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(CHARGE_SOURCE, Direction.NORTH).with(CHARGED, false));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(CHARGED, CHARGE_SOURCE, TYPE, WATERLOGGED);
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
		switch(stateIn.get(TYPE)) {
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
		SlabType type = stateIn.get(TYPE);
		if (type == SlabType.TOP && faceOut == Direction.DOWN) return stateIn;
		if (type == SlabType.BOTTOM && faceOut == Direction.UP) return stateIn;
		return stateIn.with(CHARGE_SOURCE, faceOut);
	}

}