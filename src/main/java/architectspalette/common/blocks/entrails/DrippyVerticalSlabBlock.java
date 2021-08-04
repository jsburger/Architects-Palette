package architectspalette.common.blocks.entrails;

import architectspalette.common.blocks.VerticalSlabBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class DrippyVerticalSlabBlock extends VerticalSlabBlock {
    public DrippyVerticalSlabBlock(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        DrippyBlock.doParticleEffect(stateIn, worldIn, pos, rand);
    }

}
