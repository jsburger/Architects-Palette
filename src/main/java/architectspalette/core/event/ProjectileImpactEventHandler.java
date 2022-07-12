package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.MiscRegistry;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
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

    @SubscribeEvent
    public static void projectileImpact(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        if (projectile.getDeltaMovement().length() > .2 && event.getRayTraceResult() instanceof BlockHitResult hitResult) {
            BlockState state = projectile.getLevel().getBlockState(hitResult.getBlockPos());
            if (state.is(MiscRegistry.WIZARD_BLOCKS)) {
                event.setCanceled(true);

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

                double dot = motion.dot(normal);
                //Attempting a flat reduction of the force rather than a multiplicative one. Not quite right, but it works.
                //For proper mathematical reflection, you would not include this part, but arrows are weird so this was necessary.
                if (-dot > .05) {
                    dot += .05;
                }
                Vec3 a = normal.scale(-dot * 2);
                //Prevent excessive bouncing upwards, specifically to prevent stuff bouncing on the ground too much
                if ((a.length() < .05) && hitResult.getDirection() == Direction.UP) {
                    event.setCanceled(false);
                } else {
                    projectile.setDeltaMovement(motion.add(a));
                }

                //Recalculate rotation values, more or less copied from vanilla.
                Vec3 vec3 = projectile.getDeltaMovement();
                double d0 = vec3.horizontalDistance();
                float xrot = ((float) (Mth.atan2(vec3.y, d0) * (180F / Math.PI)));
                float yrot = ((float) (Mth.atan2(vec3.x, vec3.z) * (180F / Math.PI)));

//                Vec3 hit = event.getRayTraceResult().getLocation();
                Vec3 hit = projectile.position();
                //Apply position and rotation strictly
                projectile.absMoveTo(hit.x, hit.y, hit.z, yrot, xrot);

                //Place particle slightly off the surface.
                hit = event.getRayTraceResult().getLocation().add(normal.scale(.02));
                if (projectile.level.isClientSide) {
                    projectile.level.addParticle(MiscRegistry.WIZARDLY_DEFENSE_BLAST.get(), hit.x, hit.y, hit.z, normal.x, normal.y, normal.z);
                }

                //Check if projectile will hit another block, if so, line it up so that it doesn't end up inside
                BlockHitResult check = projectile.level.clip(new ClipContext(projectile.position(), projectile.position().add(vec3), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, projectile));
                if (check.getType() != HitResult.Type.MISS && !check.isInside() && (check.getBlockPos() != hitResult.getBlockPos())) {
                    projectile.setPos(check.getLocation().subtract(vec3.scale(1.1)));
                }
            }
        }
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
