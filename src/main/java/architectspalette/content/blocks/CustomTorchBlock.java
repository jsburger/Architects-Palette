package architectspalette.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.function.Supplier;

//This class is only required because registry order is static, and particles come after blocks. The normal torch constructor cannot take a modded particle.
public class CustomTorchBlock extends TorchBlock {

    protected final Supplier<SimpleParticleType> flameGetter;

    public CustomTorchBlock(Properties properties, Supplier<SimpleParticleType> particleGetter) {
        super(properties, ParticleTypes.FLAME);
        flameGetter = particleGetter;
    }

    @Override
    public void animateTick(BlockState p_57494_, Level p_57495_, BlockPos p_57496_, Random p_57497_) {
        double d0 = (double)p_57496_.getX() + 0.5D;
        double d1 = (double)p_57496_.getY() + 0.7D;
        double d2 = (double)p_57496_.getZ() + 0.5D;
        p_57495_.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        p_57495_.addParticle(this.flameGetter.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

}

