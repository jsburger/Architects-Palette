package architectspalette.common.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IAbyssalineChargeable {
    default boolean isCharged(BlockState stateIn){
        return stateIn.getValue(NewAbyssalineBlock.CHARGED);
    }
    default Direction getSourceDirection(BlockState stateIn) {
        return stateIn.getValue(NewAbyssalineBlock.CHARGE_SOURCE);
    }
    //stateIn is the block accepting or outputting charge, faceIn is the side that the block asking is on
    default boolean outputsChargeFrom(BlockState stateIn, Direction faceIn) {
        return faceIn != getSourceDirection(stateIn) && isCharged(stateIn);
    }
    default boolean acceptsChargeFrom(BlockState stateIn, Direction faceIn) {
        return true;
    }
    default BlockPos getSourceOffset(BlockState stateIn) {
        return new BlockPos(getSourceDirection(stateIn).getNormal());
    }
    default BlockState getStateWithCharge(BlockState stateIn, boolean charged) {
        return stateIn.setValue(NewAbyssalineBlock.CHARGED, charged);
    }
    //faceOut is the place that power is coming from, so it should point to that.
    default BlockState getStateWithChargeDirection(BlockState stateIn, Direction faceOut) {
        return stateIn.setValue(NewAbyssalineBlock.CHARGE_SOURCE, faceOut);
    }
    //if true, block's power will supersede the other's accept charge check, if possible
    default boolean pushesPower(BlockState stateIn) {
        return false;
    }
    default boolean pullsPowerFrom(BlockState stateIn, Direction faceIn) {
        return false;
    }
}
