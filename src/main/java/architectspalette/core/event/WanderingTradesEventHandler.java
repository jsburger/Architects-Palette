package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocks;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID)
public class WanderingTradesEventHandler {

    @SubscribeEvent
    public static void onWanderingTradesLoaded(WandererTradesEvent event) {
        if (APConfig.WANDERER_TRADES_ENABLED.get()) {
            List<VillagerTrades.ItemListing> generic = event.getGenericTrades();

            generic.add(new BasicItemListing(2, new ItemStack(APBlocks.SUNSTONE.get(), 6), 20, 2, 0f));
            generic.add(new BasicItemListing(2, new ItemStack(APBlocks.MOONSTONE.get(), 6), 20, 2, 0f));
        }
    }
}
