package architectspalette.common.blocks.flint;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlintBlock extends Block {

    public FlintBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 1.25f);
    }
}
