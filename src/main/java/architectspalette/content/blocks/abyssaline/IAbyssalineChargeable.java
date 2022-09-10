package architectspalette.content.blocks.abyssaline;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface IAbyssalineChargeable {

    // Map of directions to their normals. Means I'm not creating a new BlockPos every time.
    ImmutableMap<Direction, BlockPos> OFFSETS = new ImmutableMap.Builder<Direction, BlockPos>()
            .putAll(Arrays.stream(Direction.values()).collect(Collectors.toMap((a) -> a, (d) -> new BlockPos(d.getNormal()))))
            .build();

    default boolean isCharged(BlockState stateIn){
        return stateIn.getValue(AbyssalineBlock.CHARGED);
    }
    default Direction getSourceDirection(BlockState stateIn) {
        return stateIn.getValue(AbyssalineBlock.CHARGE_SOURCE);
    }
    //stateIn is the block accepting or outputting charge, faceIn is the side that the block asking is on
    default boolean outputsChargeTo(BlockState stateIn, Direction faceIn) {
        return faceIn != getSourceDirection(stateIn) && isCharged(stateIn);
    }
    default boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
        return true;
    }
    default BlockPos getSourceOffset(BlockState stateIn) {
        return OFFSETS.get(getSourceDirection(stateIn));
    }
    default BlockState getStateWithCharge(BlockState stateIn, boolean charged) {
        return stateIn.setValue(AbyssalineBlock.CHARGED, charged);
    }
    //directionToSource is the place that power is coming from, so it should point to that.
    default BlockState getStateWithChargeDirection(BlockState stateIn, Direction directionToSource) {
        return stateIn.setValue(AbyssalineBlock.CHARGE_SOURCE, directionToSource);
    }
    //if true, block's power will supersede the other's accept charge check, if possible
    default boolean pushesPower(BlockState stateIn) {
        return false;
    }
    default boolean pullsPowerFrom(BlockState stateIn, Direction faceIn) {
        return false;
    }
}
