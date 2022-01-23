package architectspalette.common.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

public class AbyssalineBrickWallBlock extends WallBlock {

	public AbyssalineBrickWallBlock(Properties properties) {
		super(properties);
	}
	
//	@Override
//	public int getLightValue(BlockState state, BlockGetter world, BlockPos pos) {
//		return state.getValue(AbyssalineBlock.CHARGED) ? 7 : 0;
//	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return super.updateShape(state, facing, facingState, worldIn, currentPos, facingPos).setValue(AbyssalineBlock.CHARGED, AbyssalineBlock.checkForNearbyChargedBlocks(worldIn, currentPos));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(AbyssalineBlock.CHARGED, AbyssalineBlock.checkForNearbyChargedBlocks(context.getLevel(), context.getClickedPos()));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AbyssalineBlock.CHARGED);
	}

}