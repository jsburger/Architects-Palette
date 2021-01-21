package architectspalette.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

// This code was heavily referenced from Farmer's Delight's Tatami mat, with the permission of .vectorwing
// Thanks!
public class BigBrickBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final BooleanProperty PAIRED = BooleanProperty.create("paired");
    public final BrickType TYPE;

    public BigBrickBlock(AbstractBlock.Properties properties) {
        this(properties, BrickType.STONE);
    }

    public BigBrickBlock(AbstractBlock.Properties properties, BrickType type) {
        super(properties);
        this.TYPE = type;
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.DOWN).with(PAIRED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction face = context.getFace();
        BlockPos targetPos = context.getPos().offset(face.getOpposite());
        BlockState targetState = context.getWorld().getBlockState(targetPos);
        boolean pairing = false;

        if (context.getPlayer() != null && !context.getPlayer().isSneaking() && BrickMatches(this, targetState) && !targetState.get(PAIRED)) {
            pairing = true;
        }

        return this.getDefaultState().with(FACING, context.getFace().getOpposite()).with(PAIRED, pairing);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            if (placer != null && placer.isSneaking()) {
                return;
            }
            BlockPos blockpos = pos.offset(state.get(FACING));
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (BrickMatches(this, blockstate) && !blockstate.get(PAIRED)) {
                worldIn.setBlockState(blockpos, blockstate.with(FACING, state.get(FACING).getOpposite()).with(PAIRED, true), 3);
                worldIn.func_230547_a_(pos, Blocks.AIR);
                state.updateNeighbours(worldIn, pos, 3);
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
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing.equals(stateIn.get(FACING)) && stateIn.get(PAIRED) && !(BrickMatches(this, worldIn.getBlockState(facingPos)))) {
            return stateIn.with(PAIRED, false);
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAIRED);
    }

    public enum BrickType {
        STONE,
        END_STONE
    }
}

