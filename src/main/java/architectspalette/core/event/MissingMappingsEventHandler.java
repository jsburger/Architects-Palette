package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID)
public class MissingMappingsEventHandler {

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).forEach((e) -> {
            if (e.getKey().getNamespace().equals(ArchitectsPalette.MOD_ID)) {
                if (e.getKey().getPath().contains("limestone")) {
                    String newpath = e.getKey().getPath().replace("limestone", "myonite");
                    ResourceLocation newresource = new ResourceLocation(ArchitectsPalette.MOD_ID, newpath);
                    if (e.getRegistry().containsKey(newresource)) {
                        e.remap(e.getRegistry().getValue(newresource));
                        ArchitectsPalette.LOGGER.warn("Remapping {} to {}", e.getKey().toString(), newresource.toString());
                    }
                }
                else {
                    if (e.getKey().getPath().contains("heavy_dripstone") && !e.getKey().getPath().contains("bricks")) {
                        ArchitectsPalette.LOGGER.warn("Remapping Heavy Dripstone to Heavy Dripstone Bricks");
                        e.remap(e.getRegistry().getValue(new ResourceLocation(ArchitectsPalette.MOD_ID, "heavy_dripstone_bricks")));
                    }
                }
            }
        });
    }
}
