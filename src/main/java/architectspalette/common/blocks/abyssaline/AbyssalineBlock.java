package architectspalette.common.blocks.abyssaline;

import architectspalette.core.registry.APBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class AbyssalineBlock extends Block {
	public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

	public AbyssalineBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AbyssalineBlock.CHARGED, false));
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(CHARGED) ? 7 : 0;
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return this.getDefaultState().with(CHARGED, checkForNearbyChargedBlocks(worldIn, currentPos));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(CHARGED, checkForNearbyChargedBlocks(context.getWorld(), context.getPos()));
	}
	
	public static boolean checkForNearbyChargedBlocks(IWorld world, BlockPos pos) {
		boolean powered = false;
		
		for(Direction sides : Direction.values()) {
			BlockPos offset = pos.offset(sides);
			BlockState state = world.getBlockState(offset);
			if(state.getBlock() == APBlocks.CHISELED_ABYSSALINE_BRICKS.get() && state.get(CHARGED)) {
				powered = true;
				break;
			}
		}
		
		if(!powered) {
			for(Direction sides : Direction.values()) {
				BlockPos offset = pos.offset(sides);
				BlockState state = world.getBlockState(offset);
				if(isChargedBlock(state)) {
					return checkForNearbySource(world, offset, sides.getOpposite(), 1);
				}
			}
		}
		
		return powered;
	}
	
	private static boolean checkForNearbySource(IWorld world, BlockPos pos, Direction blacklistedDirection, int cycles) {
		if(cycles == 2) return false;
		boolean powered = false;
		
		for(Direction sides : Direction.values()) {
			if(sides != blacklistedDirection) {
				BlockPos offset = pos.offset(sides);
				BlockState state = world.getBlockState(offset);
				if(state.getBlock() == APBlocks.CHISELED_ABYSSALINE_BRICKS.get() && state.get(CHARGED)) {
					powered = true;
					break;
				}
			}
		}
		
		if(!powered) {
			for(Direction sides : Direction.values()) {
				BlockPos offset = pos.offset(sides);
				BlockState state = world.getBlockState(offset);
				if(isChargedBlock(state)) {
					return checkForNearbySource(world, offset, sides.getOpposite(), cycles + 1);
				}
			}
		}
		
		return powered;
	}
	
	public static boolean isChargedBlock(BlockState state) {
		return state.getBlock() != APBlocks.CHISELED_ABYSSALINE_BRICKS.get() && state.hasProperty(CHARGED) && state.get(CHARGED);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(CHARGED);
	}
}