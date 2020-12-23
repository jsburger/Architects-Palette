package architectspalette.common.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class PipeBlock extends RotatedPillarBlock implements IWaterLoggable {

    public static final EnumProperty<PipeBlockPart> PART = EnumProperty.create("part", PipeBlockPart.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape Y_AXIS_SHAPE = cutout(Block.makeCuboidShape(2, 0, 2, 14, 16, 14));
    protected static final VoxelShape Z_AXIS_SHAPE = cutout(Block.makeCuboidShape(2, 2, 0, 14, 14, 16));
    protected static final VoxelShape X_AXIS_SHAPE = cutout(Block.makeCuboidShape(0, 2, 2, 16, 14, 14));

    public PipeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(PART, PipeBlockPart.MIDDLE).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS, PART, WATERLOGGED);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(AXIS)) {
            case X:
            default:
                return X_AXIS_SHAPE;
            case Z:
                return Z_AXIS_SHAPE;
            case Y:
                return Y_AXIS_SHAPE;
        }
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        //also stole this from chains, still dunno if im supposed to
        if (state.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return state.with(PART, checkNearbyPipes(state, worldIn, currentPos));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //stole this from chains, dunno if im supposed to.
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER;

        BlockState b = super.getStateForPlacement(context);
        return b.with(PART, checkNearbyPipes(b, context.getWorld(), context.getPos())).with(WATERLOGGED, flag);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public PipeBlockPart checkNearbyPipes(BlockState state, IWorld world, BlockPos pos) {
        Direction.Axis facing = state.get(AXIS);
        // this function is just offset, but for axis
        BlockPos forward = pos.func_241872_a(facing, 1);
        BlockPos reverse = pos.func_241872_a(facing, -1);
        boolean ffound = pipeMatches(state, world.getBlockState(forward));
        boolean rfound = pipeMatches(state, world.getBlockState(reverse));

        //swap alignment checks for z axis, since they dont line up on that one correctly for some reason
        if (facing == Direction.Axis.Z) {
            boolean e = ffound;
            ffound = rfound;
            rfound = e;
        }

        if (ffound && rfound) return PipeBlockPart.MIDDLE;
        if (ffound) return PipeBlockPart.BOTTOM;
        return PipeBlockPart.TOP;
    }

    private boolean pipeMatches(BlockState base, BlockState checking) {
        return checking.getBlock() instanceof PipeBlock && checking.get(AXIS) == base.get(AXIS);
    }

    // referenced (copied) from Farmer's Delight by _vectorwing
    // cuts out voxel regions from a cube
    private static VoxelShape cutout(VoxelShape... cutouts){
        VoxelShape shape = VoxelShapes.fullCube();
        for (VoxelShape cutout : cutouts) {
            shape = VoxelShapes.combine(shape, cutout, IBooleanFunction.ONLY_FIRST);
        }
        return shape.simplify();
    }

    public enum PipeBlockPart implements IStringSerializable {
        TOP,
        MIDDLE,
        BOTTOM;

        public String toString() { return this.getString(); }

        public String getString() {
            switch(this) {
                case TOP: return "top";
                case BOTTOM: return "bottom";
                default: return "middle";
            }
        }
    }
}
