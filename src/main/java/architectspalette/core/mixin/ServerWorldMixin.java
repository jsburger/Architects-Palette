package architectspalette.core.mixin;

import architectspalette.core.crafting.WarpingHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {

    @Inject(method = "addDuringTeleport", at = @At("HEAD"))
    public void warpingCheck(Entity entity, CallbackInfo callbackInfo) {
        ServerLevel world = (ServerLevel) (Object) this;
        if (entity instanceof ItemEntity) {
            WarpingHandler.warpItem((ItemEntity) entity, world);
        }
    }
}
