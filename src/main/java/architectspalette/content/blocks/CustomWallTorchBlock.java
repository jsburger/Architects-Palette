package architectspalette.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

//This class is only required because registry order is static, and particles come after blocks. The normal torch constructor cannot take a modded particle.
public class CustomWallTorchBlock extends WallTorchBlock {

    protected final Supplier<SimpleParticleType> flameGetter;

    public CustomWallTorchBlock(Properties properties, Supplier<SimpleParticleType> particleGetter) {
        super(properties, ParticleTypes.FLAME);
        flameGetter = particleGetter;
    }

    @Override
    public void animateTick(BlockState p_58128_, Level p_58129_, BlockPos p_58130_, RandomSource p_58131_) {
        Direction direction = p_58128_.getValue(FACING);
        double d0 = (double)p_58130_.getX() + 0.5D;
        double d1 = (double)p_58130_.getY() + 0.7D;
        double d2 = (double)p_58130_.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.27D;
        Direction direction1 = direction.getOpposite();
        p_58129_.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double)direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
        p_58129_.addParticle(this.flameGetter.get(), d0 + 0.27D * (double)direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
    }

}
