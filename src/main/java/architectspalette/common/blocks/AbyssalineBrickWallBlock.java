package architectspalette.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class AbyssalineBrickWallBlock extends WallBlock {

	public AbyssalineBrickWallBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(AbyssalineBlock.CHARGED) ? 7 : 0;
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return super.updatePostPlacement(state, facing, facingState, worldIn, currentPos, facingPos).with(AbyssalineBlock.CHARGED, AbyssalineBlock.checkForNearbyChargedBlocks(worldIn, currentPos));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).with(AbyssalineBlock.CHARGED, AbyssalineBlock.checkForNearbyChargedBlocks(context.getWorld(), context.getPos()));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(AbyssalineBlock.CHARGED);
	}

}