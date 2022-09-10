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

    public static VoxelShape flipShapeVertical(VoxelShape shape) {
        VoxelShape[] newShape = {Shapes.empty()};
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> newShape[0] = Shapes.or(newShape[0], Shapes.box(minX, 1 - maxY, minZ, maxX, 1 - minY, maxZ)));

        return newShape[0];
    }


}
