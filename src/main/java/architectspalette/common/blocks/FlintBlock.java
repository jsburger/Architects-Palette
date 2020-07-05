package architectspalette.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlintBlock extends Block {

    public static float fallDamageMultiplier = 1.25f;

    public FlintBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, fallDamageMultiplier);
    }
}
