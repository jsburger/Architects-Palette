package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID)
public class MissingMappingsEventHandler {

    //private static void trace(String string) {ArchitectsPalette.LOGGER.warn(string);}

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        //this shit does NOT work and i DONT know why but all my worlds that had mappable blocks no longer have mappable blocks
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).forEach((e) -> {
            var name = e.getKey().getPath();
            //trace("mapping " + name);
            if (e.getKey().getNamespace().equals(ArchitectsPalette.MOD_ID)) {
                //trace(name + " is from the mod");
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
                if (name.contains("albert")) {
                    ///trace("contains albert");
                    String newpath = name.replace("albert", "moonshale");
                    //trace(newpath);
                    ResourceLocation newresource = new ResourceLocation(ArchitectsPalette.MOD_ID, newpath);
                    e.remap(e.getRegistry().getValue(newresource));
                    if (e.getRegistry().containsKey(newresource)) {

                        ArchitectsPalette.LOGGER.warn("Remapping {} to {}", e.getKey().toString(), newresource.toString());
                    }
                }
            }
        });
    }
}
