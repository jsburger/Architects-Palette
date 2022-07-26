package architectspalette.core.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

//The Shape Rotator has logged on
public class ShapeRotator {

    // referenced (copied) from Farmer's Delight by .vectorwing
    // cuts out voxel regions from a cube
    public static VoxelShape cutout(VoxelShape... cutouts) {
        VoxelShape shape = Shapes.block();
        for (VoxelShape cutout : cutouts) {
            shape = Shapes.joinUnoptimized(shape, cutout, BooleanOp.ONLY_FIRST);
        }
        return shape.optimize();
    }

//    public static VoxelShape join(VoxelShape base, ) {
//
//    }


    //You'll never believe where I found this.
    //I was searching the MMD for if Minecraft cached block shapes or not.
    //Turns out that some guy (goes by Max) already wrote this function and was trying to mixin it directly to the VoxelShape class.
    //Someone told him to make sure to cache the results after using this, that's why it came up in my search.
    //Crazy, right? I was going to make the points into vectors and rotate them with quaternions, but this seems way better.
    //Don't tell anyone, though. I'm not sure Max will appreciate this.
    public static VoxelShape rotateShapeHorizontal(VoxelShape shape, Direction from, Direction to) {
        if (from == to) return shape;

        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }
        return buffer[0];
    }



}
