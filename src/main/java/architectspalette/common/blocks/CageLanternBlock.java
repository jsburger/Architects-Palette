package architectspalette.common.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;
import java.util.Random;
import java.util.function.ToIntFunction;

public class CageLanternBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

    private static final Map<Direction, VoxelShape> SHAPES = new ImmutableMap.Builder<Direction, VoxelShape>()
            .put(Direction.DOWN, Block.makeCuboidShape(5, 0, 5, 11, 6, 11))
            .put(Direction.UP, Block.makeCuboidShape(5, 16, 6, 11, 10, 11))
            .put(Direction.NORTH, Block.makeCuboidShape(5, 5, 0, 11, 11, 6))
            .put(Direction.SOUTH, Block.makeCuboidShape(5, 5, 16, 11, 11, 10))
            .put(Direction.WEST, Block.makeCuboidShape(0, 5, 5, 6, 11, 11))
            .put(Direction.EAST, Block.makeCuboidShape(16, 5, 5, 10, 11, 11))
            .build();

    public CageLanternBlock(Properties properties, int poweredLightLevel) {
        super(properties.setLightLevel(getLightValueLit(poweredLightLevel)));
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(LIT, true).with(WATERLOGGED, false).with(INVERTED, true));
    }

    private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
        return (state) -> state.get(BlockStateProperties.LIT) ? lightValue : 0;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED, FACING, INVERTED);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (state.get(FACING) == facing && !state.isValidPosition(worldIn, currentPos)) {return Blocks.AIR.getDefaultState();}
        return state;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = state.get(FACING);
        return Block.hasEnoughSolidSide(worldIn, pos.offset(direction), direction.getOpposite());
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER;

        return this.getDefaultState().with(FACING, context.getFace().getOpposite()).with(WATERLOGGED, flag);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        BlockState newState = state.with(INVERTED, !state.get(INVERTED));
        worldIn.setBlockState(pos, newState.with(LIT, getLitState(newState, worldIn, pos)), 2);
        return ActionResultType.func_233537_a_(worldIn.isRemote);
    }

    private boolean getLitState(BlockState state, World world, BlockPos pos) {
        return state.get(INVERTED) ^ world.isBlockPowered(pos);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            boolean lit = state.get(LIT);
            boolean shouldBeLit = getLitState(state, worldIn, pos);
            if (lit != shouldBeLit) {
                if (lit) {
                    worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
                    worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2);
                } else {
                    // fuck if i know what this does, i copied it from the redstone lamp
                    worldIn.setBlockState(pos, state.func_235896_a_(LIT), 2);
                }
            }
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(FACING));
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        boolean shouldBeLit = getLitState(state, worldIn, pos);
        if (shouldBeLit != state.get(LIT)) {
            worldIn.setBlockState(pos, state.with(LIT, shouldBeLit), 2);
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }


}
