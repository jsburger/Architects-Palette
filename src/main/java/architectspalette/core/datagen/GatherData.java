package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GatherData {

    public static void load() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(true, new Advancements(generator));
            var blocktagger = new APBlockTags(generator, ArchitectsPalette.MOD_ID, event.getExistingFileHelper());
            generator.addProvider(true, blocktagger);
            generator.addProvider(true, new APItemTags(generator, blocktagger, ArchitectsPalette.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(true, new APLootTables(generator));
            generator.addProvider(true, new APRecipes(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(true, new Blockstates(generator, ArchitectsPalette.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(true, new APLang(generator));
        }
    }
}
