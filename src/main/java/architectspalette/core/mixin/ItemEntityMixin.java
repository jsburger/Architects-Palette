package architectspalette.core.mixin;

import architectspalette.common.event.ChangeDimensionHandler;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "changeDimension", at = @At("HEAD"), remap = false)
    private void changeDimension(ServerWorld server, net.minecraftforge.common.util.ITeleporter teleporter, CallbackInfoReturnable ci) {
        ChangeDimensionHandler.onDimensionsChanged((ItemEntity)(Object)this, server);
    }
}
