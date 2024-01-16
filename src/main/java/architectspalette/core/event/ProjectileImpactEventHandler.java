package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.APSounds;
import architectspalette.core.registry.MiscRegistry;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ProjectileImpactEventHandler {

    // Used in evil arrow mixin
    public static boolean justHitWardstone = false;

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        if (projectile.getDeltaMovement().length() > .2 && event.getRayTraceResult() instanceof BlockHitResult hitResult) {
            if (deflect(projectile, hitResult, 0)) event.setCanceled(true);
        }
    }

    private static boolean deflect(Projectile projectile, BlockHitResult hitResult, int depth) {
        BlockState state = projectile.level().getBlockState(hitResult.getBlockPos());
        Level level = projectile.level();
        if (state.is(MiscRegistry.WIZARD_BLOCKS)) {
            justHitWardstone = false;

            //Get normal (atLowerCorner is just to cast it from Vec3i, I'm lazy)
            Vec3 normal = Vec3.atLowerCornerOf(hitResult.getDirection().getNormal());

            //Funny checks to make stairs act as slopes
            if (state.getBlock() instanceof StairBlock) {
                Vec3 stairsVector = doStairsMath(hitResult.getDirection(), state);
                if (stairsVector != null) {
                    normal = normal.add(stairsVector).normalize();
                }
            }

            Vec3 motion = projectile.getDeltaMovement();

            double previousSpeed = motion.length();
            double dot = motion.dot(normal);
            //Attempting a flat reduction of the force rather than a multiplicative one. Not quite right, but it works.
            //For proper mathematical reflection, you would not include this part, but arrows are weird so this was necessary.
            if (-dot > .05) {
                dot += .05;
            }
            Vec3 a = normal.scale(-dot * 2);
            //Prevent excessive bouncing upwards, specifically to prevent stuff bouncing on the ground too much
            if ((a.length() < .05) && hitResult.getDirection() == Direction.UP) {
                return false;
            } else {
                projectile.setDeltaMovement(motion.add(a));
                if (projectile instanceof AbstractArrow) justHitWardstone = true;
            }

            //Recalculate rotation values, more or less copied from vanilla.
            Vec3 newMotion = projectile.getDeltaMovement();
            double d0 = newMotion.horizontalDistance();
            float xrot = ((float) (Mth.atan2(newMotion.y, d0) * (180F / Math.PI)));
            float yrot = ((float) (Mth.atan2(newMotion.x, newMotion.z) * (180F / Math.PI)));

//            Vec3 diff = hitResult.getLocation().subtract(projectile.position());
//            double speedDiff = previousSpeed - newMotion.length();
            Vec3 moveTo = projectile.position();
//            moveTo = moveTo.add(normal.scale(2 * diff.dot(normal))).add(newMotion.normalize().scale(2 * speedDiff));

            //Apply position and rotation strictly
            projectile.absMoveTo(moveTo.x, moveTo.y, moveTo.z, yrot, xrot);
            //if (projectile.getBoundingBox().contains(hitResult.getLocation())) projectile.moveTo(projectile.position().add(normal.scale(projectile.getBbWidth())));

            //Place particle slightly off the surface.
            Vec3 hit = hitResult.getLocation().add(normal.scale(.02));
            if (level.isClientSide) {
                level.addParticle(MiscRegistry.WIZARDLY_DEFENSE_BLAST.get(), hit.x, hit.y, hit.z, normal.x, normal.y, normal.z);
                level.playLocalSound(hit.x, hit.y, hit.z, APSounds.WIZARD_BLAST.get(), SoundSource.BLOCKS, .5f, level.random.nextFloat() * .4f + .8f, false);
            }

//            if (depth < 10) {
//                BlockHitResult check = level.clip(new ClipContext(hitResult.getLocation(), projectile.position().add(newMotion), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, projectile));
//                if (check.getType() != HitResult.Type.MISS && !check.isInside() && (check.getBlockPos() != hitResult.getBlockPos()) && (check.getLocation().subtract(hitResult.getLocation()).length() > .2)) {
//                    deflect(projectile, check, depth + 1);
//                }
//            }

            //Check if projectile will hit another block, if so, line it up so that it doesn't end up inside
            BlockHitResult check = level.clip(new ClipContext(projectile.position(), projectile.position().add(newMotion), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, projectile));
            if (check.getType() != HitResult.Type.MISS && !check.isInside() && (check.getBlockPos() != hitResult.getBlockPos())) {
                projectile.setPos(check.getLocation().subtract(newMotion.scale(1.1)));
            }
            return true;
        }
        return false;
    }

    private static Vec3 doStairsMath(Direction direction, BlockState state) {
        Half half = state.getValue(StairBlock.HALF);
        Vec3 e = null;
        if (direction == state.getValue(StairBlock.FACING).getOpposite()) {
            e = half == Half.BOTTOM ? new Vec3(0, 1, 0) : new Vec3(0, -1, 0);
        }
        if ((direction == Direction.UP && half == Half.BOTTOM) || (direction == Direction.DOWN && half == Half.TOP)) {
            e = Vec3.atLowerCornerOf(state.getValue(StairBlock.FACING).getOpposite().getNormal());
        }
        return e;
    }
}
