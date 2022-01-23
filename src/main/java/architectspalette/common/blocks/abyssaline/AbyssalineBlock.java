package architectspalette.common.blocks.abyssaline;

import architectspalette.core.registry.APBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class AbyssalineBlock extends Block {
	public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

	public AbyssalineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AbyssalineBlock.CHARGED, false));
	}
	
//	@Override
//	public int getLightValue(BlockState state, BlockGetter world, BlockPos pos) {
//		return state.getValue(CHARGED) ? 7 : 0;
//	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return this.defaultBlockState().setValue(CHARGED, checkForNearbyChargedBlocks(worldIn, currentPos));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(CHARGED, checkForNearbyChargedBlocks(context.getLevel(), context.getClickedPos()));
	}
	
	public static boolean checkForNearbyChargedBlocks(LevelAccessor world, BlockPos pos) {
		boolean powered = false;
		
		for(Direction sides : Direction.values()) {
			BlockPos offset = pos.relative(sides);
			BlockState state = world.getBlockState(offset);
			if(state.getBlock() == APBlocks.CHISELED_ABYSSALINE_BRICKS.get() && state.getValue(CHARGED)) {
				powered = true;
				break;
			}
		}
		
		if(!powered) {
			for(Direction sides : Direction.values()) {
				BlockPos offset = pos.relative(sides);
				BlockState state = world.getBlockState(offset);
				if(isChargedBlock(state)) {
					return checkForNearbySource(world, offset, sides.getOpposite(), 1);
				}
			}
		}
		
		return powered;
	}
	
	private static boolean checkForNearbySource(LevelAccessor world, BlockPos pos, Direction blacklistedDirection, int cycles) {
		if(cycles == 2) return false;
		boolean powered = false;
		
		for(Direction sides : Direction.values()) {
			if(sides != blacklistedDirection) {
				BlockPos offset = pos.relative(sides);
				BlockState state = world.getBlockState(offset);
				if(state.getBlock() == APBlocks.CHISELED_ABYSSALINE_BRICKS.get() && state.getValue(CHARGED)) {
					powered = true;
					break;
				}
			}
		}
		
		if(!powered) {
			for(Direction sides : Direction.values()) {
				BlockPos offset = pos.relative(sides);
				BlockState state = world.getBlockState(offset);
				if(isChargedBlock(state)) {
					return checkForNearbySource(world, offset, sides.getOpposite(), cycles + 1);
				}
			}
		}
		
		return powered;
	}
	
	public static boolean isChargedBlock(BlockState state) {
		return state.getBlock() != APBlocks.CHISELED_ABYSSALINE_BRICKS.get() && state.hasProperty(CHARGED) && state.getValue(CHARGED);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(CHARGED);
	}
}