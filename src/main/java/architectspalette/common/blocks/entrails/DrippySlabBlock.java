package architectspalette.common.blocks.entrails;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class DrippySlabBlock extends SlabBlock {
    public DrippySlabBlock(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        DrippyBlock.doParticleEffect(stateIn, worldIn, pos, rand);
    }
}