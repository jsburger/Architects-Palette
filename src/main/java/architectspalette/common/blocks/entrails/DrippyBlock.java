package architectspalette.common.blocks.entrails;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class DrippyBlock extends Block {
    public DrippyBlock(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        doParticleEffect(stateIn, worldIn, pos, rand);
    }

    @OnlyIn(Dist.CLIENT)
    public static void doParticleEffect(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(6) == 0) {
            Direction direction = Direction.getRandomDirection(rand);
            if (direction != Direction.UP && direction != Direction.DOWN) {
                BlockPos posBelow = pos.offset(Direction.DOWN);
                BlockState stateBelow = worldIn.getBlockState(posBelow);
                if (!stateIn.isSolid() || !stateBelow.isSolidSide(worldIn, posBelow, Direction.UP)) {
                    double xOffset = direction.getXOffset() == 0 ? rand.nextDouble() : 0.5D + (double)direction.getXOffset() * 0.45D;
                    double zOffset = direction.getZOffset() == 0 ? rand.nextDouble() : 0.5D + (double)direction.getZOffset() * 0.45D;
                    Vector3d dropPos = new Vector3d(pos.getX() + xOffset, pos.getY() - .1, pos.getZ() + zOffset);
                    BlockRayTraceResult rayTraceResult = stateIn.getRenderShape(worldIn, pos).rayTrace(dropPos, dropPos.add(0, 1, 0), pos);
                    if (rayTraceResult != null && rayTraceResult.getType() != RayTraceResult.Type.MISS) {
                        Vector3d result = rayTraceResult.getHitVec();
                        worldIn.addParticle(ParticleTypes.DRIPPING_WATER, result.getX(), result.getY() - .1, result.getZ(), 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }
}
