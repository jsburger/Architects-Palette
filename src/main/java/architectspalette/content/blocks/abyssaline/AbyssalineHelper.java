package architectspalette.content.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class AbyssalineHelper {

    public static final int CHARGE_LIGHT = 1;
    private static final int RECURSION_MAX = 12;

    public static boolean needsPostProcessing(BlockState stateIn, BlockGetter reader, BlockPos pos) {
        return ((IAbyssalineChargeable) stateIn.getBlock()).isCharged(stateIn);
    }

    public static boolean allowsMobSpawning(BlockState stateIn, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
        return !((IAbyssalineChargeable) stateIn.getBlock()).isCharged(stateIn);
    }

    public static boolean isAbyssaline(BlockState stateIn) {
        return stateIn.getBlock() instanceof IAbyssalineChargeable;
    }

    public static boolean getCharged(BlockState stateIn) {
        if (!isAbyssaline(stateIn)) return false;
        IAbyssalineChargeable block = (IAbyssalineChargeable) stateIn.getBlock();
        return block.isCharged(stateIn);
    }

    @Nullable
    private static IAbyssalineChargeable getAbyssaline(BlockState state) {
        if (state.getBlock() instanceof IAbyssalineChargeable chargeable) {
            return chargeable;
        }
        return null;
    }

    public static boolean isValidConnection(BlockState unpoweredState, BlockState poweringState, Direction unpoweredFace) {
        IAbyssalineChargeable toCharge = getAbyssaline(unpoweredState);
        IAbyssalineChargeable chargeSource = getAbyssaline(poweringState);

        if (toCharge == null || chargeSource == null) return false;
        if (!chargeSource.isCharged(poweringState)) return false;

        // Check if normal connection could be made.
        if ((toCharge.acceptsChargeFrom(unpoweredState, unpoweredFace) || chargeSource.pushesPower(poweringState))
            && (chargeSource.outputsChargeTo(poweringState, unpoweredFace.getOpposite()) || toCharge.pullsPowerFrom(unpoweredState, unpoweredFace))) {
            // Check if the new state can accept charge from the source. Covers edge cases like Nubs being rotated by pillars pushing power into them.
            return toCharge.acceptsChargeFrom(toCharge.getStateWithChargeDirection(unpoweredState, unpoweredFace), unpoweredFace);
        }
        return false;
    }

    @Nullable
    public static BlockState getChargedState(BlockState stateIn, boolean charge) {
        if (!isAbyssaline(stateIn)) return null;
        return ((IAbyssalineChargeable) stateIn.getBlock()).getStateWithCharge(stateIn, charge);
    }

    @Nullable
    public static BlockState getStateWithChargeDirection(BlockState stateIn, Direction faceOut) {
        if (!isAbyssaline(stateIn)) return null;
        return ((IAbyssalineChargeable) stateIn.getBlock()).getStateWithChargeDirection(stateIn, faceOut);
    }

    public static boolean createsChargeLoop(BlockState startState, LevelAccessor world, BlockPos pos) {
        BlockPos.MutableBlockPos accumulator = new BlockPos(0, 0, 0).mutable();
        return checkForLoop(startState, world, pos, RECURSION_MAX, accumulator, startState);
    }

    private static boolean checkForLoop(BlockState startState, LevelAccessor world, BlockPos pos, int tries, BlockPos.MutableBlockPos accumulator, BlockState chainStarter) {
        // These blocks form chains, if any block isn't a part, the chain should break
        if (!(startState.getBlock() instanceof IAbyssalineChargeable startBlock))
            return false;

        BlockPos offset = startBlock.getSourceOffset(startState);
        BlockPos nextPos = pos.offset(offset);
        // The only blocks with no offset are charge sources, so end the search, the source is found
        if (nextPos == pos)
            return false;

        // If the total change in position is 0, that means it has done a loop, so break the chain
        if (isAllZero(accumulator.move(offset.getX(), offset.getY(), offset.getZ())))
            return true;
        // Get the next block to check.
        BlockState nextState = world.getBlockState(nextPos);
        // If the magnitude is one, that means the chain has come back adjacent to the block, which means another direction can check the source better.
        // Has the side effect of stopping some loops early and making placing while charged more consistent.
        if (isMagnitudeOne(accumulator) && tries < RECURSION_MAX) {
            // Need to check if the side in question is even a possible route, though.
            Direction facing = directionFromOffset(accumulator);
            if (isValidConnection(chainStarter, nextState, facing)) {
                //Since there is a valid connection available here, return true here so that the original direction is skipped.
                return true;
            }
        }

        // If the next block isn't charged, it doesn't lead back to a source, so break the chain
        if (!getCharged(nextState))
            return true;
        if (--tries > 0) return checkForLoop(nextState, world, nextPos, tries, accumulator, chainStarter);
        // If out of tries, it likely isn't a loop.
        return false;
    }


    private static boolean isAllZero(BlockPos pos) {
        return pos.getY() == 0 && pos.getX() == 0 && pos.getZ() == 0;
    }

    private static boolean isMagnitudeOne(BlockPos pos) {
        return (Math.abs(pos.getY()) + Math.abs(pos.getX()) + Math.abs(pos.getZ())) == 1;
    }

    // Hate this function
    private static Direction directionFromOffset(BlockPos pos) {
        final int x = Math.abs(pos.getX());
        final int y = Math.abs(pos.getY());
        final int z = Math.abs(pos.getZ());
        final int max = Math.max(Math.max(x, y), z);
        if (x == max)
            return pos.getX() > 0 ? Direction.EAST : Direction.WEST;
        if (y == max)
            return pos.getY() > 0 ? Direction.UP : Direction.DOWN;
        return pos.getZ() > 0 ? Direction.SOUTH : Direction.NORTH;
    }

    public static BlockState getStateWithNeighborCharge(BlockState stateIn, LevelAccessor world, BlockPos pos) {
        Direction source = null;
        boolean powered = false;

        for (Direction side : Direction.values()) {
            BlockPos offset = pos.relative(side);
            BlockState state = world.getBlockState(offset);
            if (isValidConnection(stateIn, state, side) && !createsChargeLoop(getStateWithChargeDirection(stateIn, side), world, pos)) {
                source = side;
                powered = true;
                break;
            }
        }
        if (source != null) {
            return getChargedState(getStateWithChargeDirection(stateIn, source), powered);
        }
        return getChargedState(stateIn, false);
    }

    public static void abyssalineTick(BlockState state, ServerLevel worldIn, BlockPos pos) {
        BlockState newState = getStateWithNeighborCharge(state, worldIn, pos);
        if (newState != state) {
            worldIn.setBlock(pos, newState, 4 | 3);
        }
    }

    public static void abyssalineNeighborUpdate(IAbyssalineChargeable thiz, BlockState stateIn, Level worldIn, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        boolean interested = (!thiz.isCharged(stateIn) && neighborBlock instanceof IAbyssalineChargeable)
                || !isValidConnection(stateIn, worldIn.getBlockState(pos.offset(thiz.getSourceOffset(stateIn))), thiz.getSourceDirection(stateIn));
        if (neighborPos.equals(pos.offset(thiz.getSourceOffset(stateIn))) || interested)
            worldIn.scheduleTick(pos, (Block) thiz, 1);
    }


}
