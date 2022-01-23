package architectspalette.common.blocks.abyssaline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class AbyssalineHelper {

    public static final int CHARGE_LIGHT = 1;
    private static final int RECURSION_MAX = 12;

    public static boolean needsPostProcessing(BlockState stateIn, IBlockReader reader, BlockPos pos) {
        return ((IAbyssalineChargeable) stateIn.getBlock()).isCharged(stateIn);
    }

    public static boolean allowsMobSpawning(BlockState stateIn, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
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

    public static boolean getOutputsFrom(BlockState stateIn, Direction faceIn) {
        if (!isAbyssaline(stateIn)) return false;
        IAbyssalineChargeable block = (IAbyssalineChargeable) stateIn.getBlock();
        return block.outputsChargeFrom(stateIn, faceIn);
    }

    public static boolean getAcceptsCharge(BlockState stateIn, Direction faceIn) {
        if (!isAbyssaline(stateIn)) return false;
        IAbyssalineChargeable block = (IAbyssalineChargeable) stateIn.getBlock();
        return block.acceptsChargeFrom(stateIn, faceIn);
    }

    public static boolean getPushesPower(BlockState stateIn) {
        if (!isAbyssaline(stateIn)) return false;
        return ((IAbyssalineChargeable) stateIn.getBlock()).pushesPower(stateIn);
    }

    public static boolean getPullsPower(BlockState stateIn, Direction faceIn) {
        if (!isAbyssaline(stateIn)) return false;
        return ((IAbyssalineChargeable) stateIn.getBlock()).pullsPowerFrom(stateIn, faceIn);
    }

    public static boolean isValidConnection(BlockState unpoweredState, BlockState poweringState, Direction unpoweredFace) {
        return (getAcceptsCharge(unpoweredState, unpoweredFace) || getPushesPower(poweringState))
                && (getOutputsFrom(poweringState, unpoweredFace.getOpposite()) || (getCharged(poweringState) && getPullsPower(unpoweredState, unpoweredFace)));
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

    public static boolean createsChargeLoop(BlockState startState, IWorld world, BlockPos pos) {
        BlockPos.Mutable accumulator = new BlockPos(0, 0, 0).toMutable();
        return checkForLoop(startState, world, pos, RECURSION_MAX, accumulator, startState);
    }

    private static boolean checkForLoop(BlockState startState, IWorld world, BlockPos pos, int tries, BlockPos.Mutable accumulator, BlockState chainStarter) {
        // These blocks form chains, if any block isn't a part, the chain should break
        if (!(startState.getBlock() instanceof IAbyssalineChargeable))
            return false;

        IAbyssalineChargeable startBlock = (IAbyssalineChargeable) startState.getBlock();

        BlockPos offset = startBlock.getSourceOffset(startState);
        BlockPos nextPos = pos.add(offset);
        // The only blocks with no offset are charge sources, so end the search, the source is found
        if (nextPos == pos)
            return false;

        // If the total change in position is 0, that means it has done a loop, so break the chain
        if (isAllZero(accumulator.move(offset.getX(), offset.getY(), offset.getZ())))
            return true;
        // If the magnitude is one, that means the chain has come back adjacent to the block, which means another direction can check the source better.
        // Has the side effect of stopping some loops early and making placing while charged more consistent
        if (isMagnitudeOne(accumulator) && tries < RECURSION_MAX) {
            // Need to check if the side in question is even a possible route, though.
            Direction facing = directionFromOffset(accumulator).getOpposite();
            if (isValidConnection(getStateWithChargeDirection(chainStarter, facing), world.getBlockState(pos.offset(facing)), facing)) {
                return true;
            }
        }

        BlockState nextState = world.getBlockState(nextPos);
        // If the block isn't charged, it doesn't lead back to a source, so break the chain
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

    public static BlockState getStateWithNeighborCharge(BlockState stateIn, IWorld world, BlockPos pos) {
        Direction source = null;
        boolean powered = false;

        for (Direction side : Direction.values()) {
            BlockPos offset = pos.offset(side);
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

    public static void abyssalineTick(BlockState state, ServerWorld worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, getStateWithNeighborCharge(state, worldIn, pos), 4 | 3);
    }

    public static void abyssalineNeighborUpdate(IAbyssalineChargeable thiz, BlockState stateIn, World worldIn, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        boolean interested = (!thiz.isCharged(stateIn) && neighborBlock instanceof IAbyssalineChargeable)
                || !isValidConnection(stateIn, worldIn.getBlockState(pos.add(thiz.getSourceOffset(stateIn))), thiz.getSourceDirection(stateIn));
        if (neighborPos.equals(pos.add(thiz.getSourceOffset(stateIn))) || interested)
            worldIn.getPendingBlockTicks().scheduleTick(pos, (Block) thiz, 1);
    }


}
