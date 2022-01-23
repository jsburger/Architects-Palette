package architectspalette.common.blocks;

import architectspalette.core.config.APConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * From Quark
 * Original: https://github.com/Vazkii/Quark/blob/fae8f48eae8157424dcf4f97f9be186d567a344d/src/main/java/vazkii/quark/content/building/block/VerticalSlabBlock.java
 * @author Vazkii
 */
public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {

	public static final String QUARK_ID = "quark";
	private static boolean quarkEnabled, checkedQuark;
	
	public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public VerticalSlabBlock(Block.Properties props) {
		super(props);

		registerDefaultState(defaultBlockState().setValue(TYPE, VerticalSlabType.NORTH).setValue(WATERLOGGED, false));
	}
	
	private static boolean isQuarkEnabled() {
		if(!checkedQuark) {
			quarkEnabled = ModList.get().isLoaded(QUARK_ID);
			checkedQuark = true;
		}
		
		return quarkEnabled || APConfig.VERTICAL_SLABS_FORCED.get();
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if(isQuarkEnabled())
			super.fillItemCategory(group, items);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if(!isQuarkEnabled())
			tooltip.add(new TranslatableComponent("architects_palette.misc.no_quark").withStyle(ChatFormatting.RED));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.getValue(TYPE) == VerticalSlabType.DOUBLE ? state : state.setValue(TYPE, VerticalSlabType.fromDirection(rot.rotate(state.getValue(TYPE).direction)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		VerticalSlabType type = state.getValue(TYPE);
		if(type == VerticalSlabType.DOUBLE || mirrorIn == Mirror.NONE)
			return state;
		
		if((mirrorIn == Mirror.LEFT_RIGHT && type.direction.getAxis() == Axis.Z)
				|| (mirrorIn == Mirror.FRONT_BACK && type.direction.getAxis() == Axis.X))
			return state.setValue(TYPE, VerticalSlabType.fromDirection(state.getValue(TYPE).direction.getOpposite()));
		
		return state;
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return state.getValue(TYPE) != VerticalSlabType.DOUBLE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}

	@Nonnull
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(TYPE).shape;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = context.getLevel().getBlockState(blockpos);
		if(blockstate.getBlock() == this) 
			return blockstate.setValue(TYPE, VerticalSlabType.DOUBLE).setValue(WATERLOGGED, false);
		
		FluidState fluid = context.getLevel().getFluidState(blockpos);
		BlockState retState = defaultBlockState().setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
		Direction direction = getDirectionForPlacement(context);
		VerticalSlabType type = VerticalSlabType.fromDirection(direction);
		
		return retState.setValue(TYPE, type);
	}
	
	private Direction getDirectionForPlacement(BlockPlaceContext context) {
		Direction direction = context.getClickedFace();
		if(direction.getAxis() != Axis.Y)
			return direction;
		
		BlockPos pos = context.getClickedPos();
		Vec3 vec = context.getClickLocation().subtract(new Vec3(pos.getX(), pos.getY(), pos.getZ())).subtract(0.5, 0, 0.5);
		double angle = Math.atan2(vec.x, vec.z) * -180.0 / Math.PI;
		return Direction.fromYRot(angle).getOpposite();
	}

	@Override
	public boolean canBeReplaced(BlockState state, @Nonnull BlockPlaceContext useContext) {
		ItemStack itemstack = useContext.getItemInHand();
		VerticalSlabType slabtype = state.getValue(TYPE);
		return slabtype != VerticalSlabType.DOUBLE && itemstack.getItem() == this.asItem()  &&
			(useContext.replacingClickedOnBlock() && (useContext.getClickedFace() == slabtype.direction && getDirectionForPlacement(useContext) == slabtype.direction)
					|| (!useContext.replacingClickedOnBlock() && useContext.getClickedFace().getAxis() != slabtype.direction.getAxis()));
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean placeLiquid(@Nonnull LevelAccessor worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull FluidState fluidStateIn) {
		return state.getValue(TYPE) != VerticalSlabType.DOUBLE && SimpleWaterloggedBlock.super.placeLiquid(worldIn, pos, state, fluidStateIn);
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return state.getValue(TYPE) != VerticalSlabType.DOUBLE && SimpleWaterloggedBlock.super.canPlaceLiquid(worldIn, pos, state, fluidIn);
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(stateIn.getValue(WATERLOGGED))
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));

		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, PathComputationType type) {
		return type == PathComputationType.WATER && worldIn.getFluidState(pos).is(FluidTags.WATER); 
	}

	public enum VerticalSlabType implements StringRepresentable {
		NORTH(Direction.NORTH),
		SOUTH(Direction.SOUTH),
		WEST(Direction.WEST),
		EAST(Direction.EAST),
		DOUBLE(null);

		private final String name;
		public final Direction direction;
		public final VoxelShape shape;

		VerticalSlabType(Direction direction) {
			this.name = direction == null ? "double" : direction.getSerializedName();
			this.direction = direction;

			if(direction == null)
				shape = Shapes.block();
			else {
				double min = 0;
				double max = 8;
				if(direction.getAxisDirection() == AxisDirection.NEGATIVE) {
					min = 8;
					max = 16;
				}

				if(direction.getAxis() == Axis.X)
					shape = Block.box(min, 0, 0, max, 16, 16);
				else shape = Block.box(0, 0, min, 16, 16, max);
			}
		}

		@Override
		public String toString() {
			return name;
		}

		@Nonnull
		@Override
		public String getSerializedName() {
			return name;
		}

		public static VerticalSlabType fromDirection(Direction direction) {
			for(VerticalSlabType type : VerticalSlabType.values())
				if(type.direction != null && direction == type.direction)
					return type;

			return null;
		}

	}

}
