package architectspalette.content.blocks.util;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class DirectionalFacingBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public DirectionalFacingBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState stateIn, Rotation rotatedBy) {
        return stateIn.setValue(FACING, rotatedBy.rotate(stateIn.getValue(FACING)));
    }

    public BlockState mirror(BlockState stateIn, Mirror mirrorAxis) {
        return stateIn.setValue(FACING, mirrorAxis.mirror(stateIn.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
    }
}
