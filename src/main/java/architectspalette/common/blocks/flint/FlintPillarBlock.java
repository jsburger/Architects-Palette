package architectspalette.common.blocks.flint;

import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlintPillarBlock extends RotatedPillarBlock {
    public FlintPillarBlock(Properties properties) {
        super(properties);
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 1.25f);
    }
}