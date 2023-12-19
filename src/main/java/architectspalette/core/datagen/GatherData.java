package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GatherData {

    //public static void load() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        var pack = generator.getPackOutput();
        if (event.includeServer()) {
            generator.addProvider(true, new ForgeAdvancementProvider(pack, event.getLookupProvider(), event.getExistingFileHelper(), List.of(new APAdvancements())));
            var blocktagger = new APBlockTags(pack, event.getLookupProvider(), event.getExistingFileHelper());
            generator.addProvider(true, blocktagger);
            generator.addProvider(true, new APItemTags(pack, event.getLookupProvider(), blocktagger, event.getExistingFileHelper()));
            generator.addProvider(true, new APLootTables(pack));
            generator.addProvider(true, new APRecipes(pack));

            generator.addProvider(true, new DatapackBuiltinEntriesProvider(pack, event.getLookupProvider(),
                    APInternalData.getRegistrySetBuilder(),
                    Set.of(ArchitectsPalette.MOD_ID)
                ));
        }
        if (event.includeClient()) {
            generator.addProvider(true, (DataProvider.Factory<Blockstates>) (packOutput) -> new Blockstates(packOutput, ArchitectsPalette.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(true, new APLang(pack));
        }
    }
}
