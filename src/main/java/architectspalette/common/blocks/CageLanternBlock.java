package architectspalette.common.blocks;

import architectspalette.core.registry.APSounds;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.Random;
import java.util.function.ToIntFunction;

public class CageLanternBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

    private static final Map<Direction, VoxelShape> SHAPES = new ImmutableMap.Builder<Direction, VoxelShape>()
            .put(Direction.DOWN, Block.box(5, 0, 5, 11, 6, 11))
            .put(Direction.UP, Block.box(5, 11, 6, 11, 16, 11))
            .put(Direction.NORTH, Block.box(5, 5, 0, 11, 11, 6))
            .put(Direction.SOUTH, Block.box(5, 5, 10, 11, 11, 16))
            .put(Direction.WEST, Block.box(0, 5, 5, 6, 11, 11))
            .put(Direction.EAST, Block.box(10, 5, 5, 16, 11, 11))
            .build();

    public CageLanternBlock(Properties properties, int poweredLightLevel) {
        super(properties.lightLevel(getLightValueLit(poweredLightLevel)));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, true).setValue(WATERLOGGED, false).setValue(INVERTED, false));
    }

    private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED, FACING, INVERTED);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        if (state.getValue(FACING) == facing && !state.canSurvive(worldIn, currentPos)) {return Blocks.AIR.defaultBlockState();}
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return Block.canSupportCenter(worldIn, pos.relative(direction), direction.getOpposite());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        boolean lit = getLitState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite()).setValue(WATERLOGGED, flag).setValue(LIT, lit);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockState newState = state.setValue(INVERTED, !state.getValue(INVERTED));
        worldIn.setBlock(pos, newState.setValue(LIT, getLitState(newState, worldIn, pos)), 2);

        SoundEvent click = state.getValue(INVERTED) ? APSounds.CAGE_LANTERN_TOGGLE_OFF.get() : APSounds.CAGE_LANTERN_TOGGLE_ON.get();
        worldIn.playSound(player, pos, click, SoundSource.BLOCKS, 1, 1);
        return InteractionResult.sidedSuccess(worldIn.isClientSide);
    }

    private boolean getLitState(BlockState state, Level world, BlockPos pos) {
        return state.getValue(INVERTED) ^ world.hasNeighborSignal(pos);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isClientSide) {
            boolean lit = state.getValue(LIT);
            boolean shouldBeLit = getLitState(state, worldIn, pos);
            if (lit != shouldBeLit) {
                if (lit) {
                    worldIn.setBlock(pos, state.cycle(LIT), 2);
                    worldIn.scheduleTick(pos, this, 2);
                } else {
                    // fuck if i know what this does, i copied it from the redstone lamp
                    worldIn.setBlock(pos, state.cycle(LIT), 2);
                }
            }
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        boolean shouldBeLit = getLitState(state, worldIn, pos);
        if (shouldBeLit != state.getValue(LIT)) {
            worldIn.setBlock(pos, state.setValue(LIT, shouldBeLit), 2);
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


}
