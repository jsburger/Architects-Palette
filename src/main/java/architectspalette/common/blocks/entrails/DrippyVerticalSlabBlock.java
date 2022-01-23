package architectspalette.common.blocks.entrails;

import architectspalette.common.blocks.VerticalSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class DrippyVerticalSlabBlock extends VerticalSlabBlock {
    public DrippyVerticalSlabBlock(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        DrippyBlock.doParticleEffect(stateIn, worldIn, pos, rand);
    }

}
