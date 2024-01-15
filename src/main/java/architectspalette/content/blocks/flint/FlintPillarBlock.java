package architectspalette.content.blocks.flint;

import architectspalette.content.blocks.util.DirectionalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FlintPillarBlock extends DirectionalFacingBlock {
    public FlintPillarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level worldIn, BlockState stateIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.causeFallDamage(fallDistance, 1.25f, worldIn.damageSources().fall());
    }
}