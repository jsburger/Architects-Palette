package architectspalette.content.blocks.entrails;

import architectspalette.content.blocks.VerticalSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DrippyVerticalSlabBlock extends VerticalSlabBlock {
    public DrippyVerticalSlabBlock(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        DrippyBlock.doParticleEffect(stateIn, worldIn, pos, rand);
    }

}
