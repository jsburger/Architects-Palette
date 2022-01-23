package architectspalette.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;

// This code was heavily referenced from Farmer's Delight's Tatami mat, with the permission of .vectorwing
// Thanks!
public class BigBrickBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final BooleanProperty PAIRED = BooleanProperty.create("paired");
    public final BrickType TYPE;

    public BigBrickBlock(BlockBehaviour.Properties properties) {
        this(properties, BrickType.STONE);
    }

    public BigBrickBlock(BlockBehaviour.Properties properties, BrickType type) {
        super(properties);
        this.TYPE = type;
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.DOWN).setValue(PAIRED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        BlockPos targetPos = context.getClickedPos().relative(face.getOpposite());
        BlockState targetState = context.getLevel().getBlockState(targetPos);
        boolean pairing = false;

        if (context.getPlayer() != null && !context.getPlayer().isShiftKeyDown() && BrickMatches(this, targetState) && !targetState.getValue(PAIRED)) {
            pairing = true;
        }

        return this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite()).setValue(PAIRED, pairing);
    }

    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isClientSide) {
            if (placer != null && placer.isShiftKeyDown()) {
                return;
            }
            BlockPos blockpos = pos.relative(state.getValue(FACING));
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (BrickMatches(this, blockstate) && !blockstate.getValue(PAIRED)) {
                worldIn.setBlock(blockpos, blockstate.setValue(FACING, state.getValue(FACING).getOpposite()).setValue(PAIRED, true), 3);
                worldIn.blockUpdated(pos, Blocks.AIR);
                state.updateNeighbourShapes(worldIn, pos, 3);
            }
        }
    }

    private boolean BrickMatches(BigBrickBlock thisBlock, BlockState suspect) {
        if (suspect.getBlock() instanceof BigBrickBlock) {
            BigBrickBlock b = (BigBrickBlock)suspect.getBlock();
            return b.TYPE == thisBlock.TYPE;
        }
        return false;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing.equals(stateIn.getValue(FACING)) && stateIn.getValue(PAIRED) && !(BrickMatches(this, worldIn.getBlockState(facingPos)))) {
            return stateIn.setValue(PAIRED, false);
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAIRED);
    }

    public enum BrickType {
        STONE,
        END_STONE
    }
}

