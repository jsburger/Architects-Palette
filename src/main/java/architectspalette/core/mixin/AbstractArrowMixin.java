package architectspalette.core.mixin;

import architectspalette.core.event.ProjectileImpactEventHandler;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    //This is used to fix behavior broken by Forge updating the projectile impact event
    //They added an option to change the impact 'result' but neglected to add one that doesn't result in the arrow landing.
    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;onHit(Lnet/minecraft/world/phys/HitResult;)V",
                    ordinal = 0
            )
    )
    private boolean architectsPaletteMixin$ShouldArrowRunOnHit(AbstractArrow instance, HitResult result) {
        return !ProjectileImpactEventHandler.justHitWardstone;
    }
    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "net/minecraft/world/entity/projectile/AbstractArrow.hasImpulse:Z",
                    ordinal = 0
            )
    )
    private boolean architectsPaletteMixin$ShouldArrowSetHasImpulse(AbstractArrow instance, boolean impulse) {
        var value = !ProjectileImpactEventHandler.justHitWardstone;
        ProjectileImpactEventHandler.justHitWardstone = false;
        return value;
    }
}
