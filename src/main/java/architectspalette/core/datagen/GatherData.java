package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GatherData {

    public static void load() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new Advancements(generator));
            generator.addProvider(new APBlockTags(generator, ArchitectsPalette.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new APLootTables(generator));
            generator.addProvider(new APRecipes(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new Blockstates(generator, ArchitectsPalette.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new APLang(generator));
        }
    }
}
