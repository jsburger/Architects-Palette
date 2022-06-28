package architectspalette.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static architectspalette.core.util.ShapeRotator.cutout;

public class PipeBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<PipeBlockPart> PART = EnumProperty.create("part", PipeBlockPart.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape Y_AXIS_SHAPE = cutout(Block.box(2, 0, 2, 14, 16, 14));
    protected static final VoxelShape Z_AXIS_SHAPE = cutout(Block.box(2, 2, 0, 14, 14, 16));
    protected static final VoxelShape X_AXIS_SHAPE = cutout(Block.box(0, 2, 2, 16, 14, 14));

    public PipeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(PART, PipeBlockPart.MIDDLE).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, PART, WATERLOGGED);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AXIS)) {
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
    public VoxelShape getInteractionShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return Shapes.block();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        //also stole this from chains, still dunno if im supposed to
        if (state.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return state.setValue(PART, checkNearbyPipes(state, worldIn, currentPos));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        //stole this from chains, dunno if im supposed to.
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;

        BlockState b = super.getStateForPlacement(context);
        return b.setValue(PART, checkNearbyPipes(b, context.getLevel(), context.getClickedPos())).setValue(WATERLOGGED, flag);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public PipeBlockPart checkNearbyPipes(BlockState state, LevelAccessor world, BlockPos pos) {
        Direction.Axis facing = state.getValue(AXIS);
        // this function is just offset, but for axis
        BlockPos forward = pos.relative(facing, 1);
        BlockPos reverse = pos.relative(facing, -1);
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
        return checking.getBlock() instanceof PipeBlock && checking.getValue(AXIS) == base.getValue(AXIS);
    }


    public enum PipeBlockPart implements StringRepresentable {
        TOP,
        MIDDLE,
        BOTTOM;

        public String toString() {
            return this.getSerializedName();
        }

        public String getSerializedName() {
            switch (this) {
                case TOP:
                    return "top";
                case BOTTOM:
                    return "bottom";
                default:
                    return "middle";
            }
        }
    }
}
