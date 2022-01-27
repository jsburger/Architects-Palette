package architectspalette.common.event;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID)
public class MissingMappingsEventHandler {

    @SubscribeEvent
    public static void onMissingMappings(RegistryEvent.MissingMappings<?> event) {
        event.getAllMappings().forEach((e) -> {
            if (e.key.getNamespace().equals(ArchitectsPalette.MOD_ID)) {
                if (e.key.getPath().contains("limestone")) {
                    String newpath = e.key.getPath().replace("limestone", "myonite");
                    ResourceLocation newresource = new ResourceLocation(ArchitectsPalette.MOD_ID, newpath);
                    if (e.registry.containsKey(newresource)) {
                        e.remap(e.registry.getValue(newresource));
                        ArchitectsPalette.LOGGER.warn("Remapping {} to {}", e.key.toString(), newresource.toString());
                    }
                }
            }
        });
    }
}
