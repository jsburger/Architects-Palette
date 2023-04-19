package architectspalette.content.blocks.abyssaline;

import architectspalette.content.blocks.NubBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.Random;

import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGED;

public class AbyssalineNubBlock extends NubBlock implements IAbyssalineChargeable {
    public AbyssalineNubBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(CHARGED, Boolean.FALSE));
    }

    public static int getLightValue(BlockState state) {
        return state.getValue(CHARGED) ? 12 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return AbyssalineHelper.getStateWithNeighborCharge(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        AbyssalineHelper.abyssalineTick(state, worldIn, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CHARGED);
    }

    @Override
    public Direction getSourceDirection(BlockState stateIn) {
        //Nubs can only be charged from the block they are on, so they don't actually need the source direction state.
        return stateIn.getValue(FACING);
    }

    @Override
    public boolean outputsChargeTo(BlockState stateIn, Direction faceIn) {
        //The nub in theory should never output power since it only accepts it from one side, and charge isn't bidirectional.
        return false;
    }

    @Override
    public boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
        //Only accepts power from the block it is on.
        return faceIn == stateIn.getValue(FACING);
    }

    @Override
    public BlockState getStateWithChargeDirection(BlockState stateIn, Direction directionToSource) {
        //Can't get power from anywhere other than a single spot, so it should never change when receiving power.
        return stateIn;
    }

    @Override
    public boolean pullsPowerFrom(BlockState stateIn, Direction faceIn) {
        //Pulls power from its back end.
        return faceIn == stateIn.getValue(FACING);
    }
}
