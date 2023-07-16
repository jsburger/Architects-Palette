package architectspalette.content.blocks;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import static architectspalette.content.blocks.BreadBlock.BreadPart.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_AXIS;

public class BreadBlock extends Block {

    public static final EnumProperty<BreadPart> PART = EnumProperty.create("part", BreadPart.class);

    public BreadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X).setValue(PART, WHOLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART, HORIZONTAL_AXIS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = super.getStateForPlacement(context);
        if (state == null) return state;
        var dir = context.getHorizontalDirection();
        var placePos = context.getClickedPos();
        var pos = new BlockPos.MutableBlockPos();

        var directions = new Direction[]{dir, dir.getOpposite(), dir.getClockWise(), dir.getCounterClockWise()};

        for (var direction : directions) {
            var test = context.getLevel().getBlockState(pos.setWithOffset(placePos, direction));
            if (test.is(this)) {
                if (canConnect(state, direction) && canConnect(test, direction.getOpposite())) {
                    state = connectTo(state, direction);
                }
            }
        }

        return state;
    }

    @Override
    @javax.annotation.Nullable
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        var stack = context.getItemInHand();
        if (!stack.canPerformAction(toolAction)) return null;
        if (ToolActions.AXE_STRIP.equals(toolAction)) return APBlocks.BREAD_BLOCK.getChild(BlockNode.BlockType.SPECIAL).get().defaultBlockState();
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (facing != Direction.UP && facing != Direction.DOWN && facingState.is(this)) {
            if (canConnect(state, facing) && canConnect(facingState, facing.getOpposite())) {
                return connectTo(state, facing);
            }
        }
        return state;
    }

    private BlockState connectTo(BlockState state, Direction direction) {
        var part = state.getValue(PART);
        if (part == WHOLE) {
            part = direction.getAxisDirection() == Direction.AxisDirection.POSITIVE ? RIGHT : LEFT;
            return state.setValue(PART, part).setValue(HORIZONTAL_AXIS, direction.getAxis());
        }
        var dir = part == LEFT ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE;
        if ((part == LEFT || part == RIGHT) && direction.getAxisDirection() == dir) {
            part = MIDDLE;
            return state.setValue(PART, part).setValue(HORIZONTAL_AXIS, direction.getAxis());
        }

        return state;
    }

    private boolean canConnect(BlockState state, Direction connectTo) {
        var part = state.getValue(PART);
        if (part == WHOLE) return true;
        /*if (part == MIDDLE)*/ return connectTo.getAxis() == state.getValue(HORIZONTAL_AXIS);
//        if (part == LEFT) return connectTo.getAxisDirection() == Direction.AxisDirection.POSITIVE && connectTo.getAxis() == state.getValue(HORIZONTAL_AXIS);
//        if (part == RIGHT) return connectTo.getAxisDirection() == Direction.AxisDirection.NEGATIVE && connectTo.getAxis() == state.getValue(HORIZONTAL_AXIS);
//        return false;
    }

    public enum BreadPart implements StringRepresentable {
        LEFT,
        MIDDLE,
        RIGHT,
        WHOLE;

        public String toString() {
            return this.getSerializedName();
        }

        public String getSerializedName() {
            return switch (this) {
                case LEFT -> "left";
                case MIDDLE -> "middle";
                case RIGHT -> "right";
                case WHOLE -> "whole";
            };
        }
    }

}
