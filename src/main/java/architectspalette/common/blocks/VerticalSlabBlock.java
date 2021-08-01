package architectspalette.common.blocks;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.ModList;

/*
 * From Quark
 * Original: https://github.com/Vazkii/Quark/blob/master/src/main/java/vazkii/quark/content/building/block/VerticalSlabBlock.java
 * @author Vazkii
 */
public class VerticalSlabBlock extends Block implements IWaterLoggable {

	public static final String QUARK_ID = "quark";
	private static boolean quarkEnabled, checkedQuark;
	
	public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public VerticalSlabBlock(Block.Properties props) {
		super(props);

		setDefaultState(getDefaultState().with(TYPE, VerticalSlabType.NORTH).with(WATERLOGGED, false));
	}
	
	private static boolean isQuarkEnabled() {
		if(!checkedQuark) {
			quarkEnabled = ModList.get().isLoaded(QUARK_ID);
			checkedQuark = true;
		}
		
		return quarkEnabled;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(isQuarkEnabled())
			super.fillItemGroup(group, items);
	}
	
	@Override
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(!isQuarkEnabled())
			tooltip.add(new TranslationTextComponent("architects_palette.misc.no_quark"));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.get(TYPE) == VerticalSlabType.DOUBLE ? state : state.with(TYPE, VerticalSlabType.fromDirection(rot.rotate(state.get(TYPE).direction)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		VerticalSlabType type = state.get(TYPE);
		if(type == VerticalSlabType.DOUBLE || mirrorIn == Mirror.NONE)
			return state;
		
		if((mirrorIn == Mirror.LEFT_RIGHT && type.direction.getAxis() == Axis.Z)
				|| (mirrorIn == Mirror.FRONT_BACK && type.direction.getAxis() == Axis.X))
			return state.with(TYPE, VerticalSlabType.fromDirection(state.get(TYPE).direction.getOpposite()));
		
		return state;
	}
	
	@Override
	public boolean isTransparent(BlockState state) {
		return state.get(TYPE) != VerticalSlabType.DOUBLE;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}

	@Nonnull
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(TYPE).shape;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		BlockState blockstate = context.getWorld().getBlockState(blockpos);
		if(blockstate.getBlock() == this) 
			return blockstate.with(TYPE, VerticalSlabType.DOUBLE).with(WATERLOGGED, false);
		
		FluidState fluid = context.getWorld().getFluidState(blockpos);
		BlockState retState = getDefaultState().with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
		Direction direction = getDirectionForPlacement(context);
		VerticalSlabType type = VerticalSlabType.fromDirection(direction);
		
		return retState.with(TYPE, type);
	}
	
	private Direction getDirectionForPlacement(BlockItemUseContext context) {
		Direction direction = context.getFace();
		if(direction.getAxis() != Axis.Y)
			return direction;
		
		BlockPos pos = context.getPos();
		Vector3d vec = context.getHitVec().subtract(new Vector3d(pos.getX(), pos.getY(), pos.getZ())).subtract(0.5, 0, 0.5);
		double angle = Math.atan2(vec.x, vec.z) * -180.0 / Math.PI;
		return Direction.fromAngle(angle).getOpposite();
	}

	@Override
	public boolean isReplaceable(BlockState state, @Nonnull BlockItemUseContext useContext) {
		ItemStack itemstack = useContext.getItem();
		VerticalSlabType slabtype = state.get(TYPE);
		return slabtype != VerticalSlabType.DOUBLE && itemstack.getItem() == this.asItem()  &&
			(useContext.replacingClickedOnBlock() && (useContext.getFace() == slabtype.direction && getDirectionForPlacement(useContext) == slabtype.direction)
					|| (!useContext.replacingClickedOnBlock() && useContext.getFace().getAxis() != slabtype.direction.getAxis()));
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean receiveFluid(@Nonnull IWorld worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull FluidState fluidStateIn) {
		return state.get(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return state.get(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if(stateIn.get(WATERLOGGED))
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean allowsMovement(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, PathType type) {
		return type == PathType.WATER && worldIn.getFluidState(pos).isTagged(FluidTags.WATER); 
	}

	public enum VerticalSlabType implements IStringSerializable {
		NORTH(Direction.NORTH),
		SOUTH(Direction.SOUTH),
		WEST(Direction.WEST),
		EAST(Direction.EAST),
		DOUBLE(null);

		private final String name;
		public final Direction direction;
		public final VoxelShape shape;

		VerticalSlabType(Direction direction) {
			this.name = direction == null ? "double" : direction.getString();
			this.direction = direction;

			if(direction == null)
				shape = VoxelShapes.fullCube();
			else {
				double min = 0;
				double max = 8;
				if(direction.getAxisDirection() == AxisDirection.NEGATIVE) {
					min = 8;
					max = 16;
				}

				if(direction.getAxis() == Axis.X)
					shape = Block.makeCuboidShape(min, 0, 0, max, 16, 16);
				else shape = Block.makeCuboidShape(0, 0, min, 16, 16, max);
			}
		}

		@Override
		public String toString() {
			return name;
		}

		@Nonnull
		@Override
		public String getString() {
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
