package architectspalette.content.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import static net.minecraft.world.level.block.RotatedPillarBlock.AXIS;

public class GlassLikePillarBlock extends AbstractGlassBlock {

    public GlassLikePillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
    }

    public BlockState rotate(BlockState p_55930_, Rotation p_55931_) {
        return rotatePillar(p_55930_, p_55931_);
    }

    public static BlockState rotatePillar(BlockState stateIn, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (stateIn.getValue(AXIS)) {
                case X -> stateIn.setValue(AXIS, Direction.Axis.Z);
                case Z -> stateIn.setValue(AXIS, Direction.Axis.X);
                default -> stateIn;
            };
            default -> stateIn;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }

    //Cullface behavior
    public boolean skipRendering(BlockState stateIn, BlockState neighborState, Direction direction) {
        return (neighborState.is(this) && stateIn.getValue(AXIS).test(direction) && stateIn.getValue(AXIS) == neighborState.getValue(AXIS));
    }

    //Doesn't work because of full block collision. Might revisit.
//    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
//        //Nifty java thing intelliJ taught me. Checks for safe cast, then casts to target in one go.
//        if (entity instanceof LivingEntity target) {
//            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 19, 0));
//        }
//    }
}
