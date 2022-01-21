package architectspalette.common.event;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocks;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID)
public class WanderingTradesEventHandler {

    @SubscribeEvent
    public static void onWanderingTradesLoaded(WandererTradesEvent event) {
        if (APConfig.WANDERER_TRADES_ENABLED.get()) {
            List<VillagerTrades.ITrade> generic = event.getGenericTrades();

            generic.add(new BasicTrade(2, new ItemStack(APBlocks.SUNSTONE.get(), 6), 20, 2, 0f));
            generic.add(new BasicTrade(2, new ItemStack(APBlocks.MOONSTONE.get(), 6), 20, 2, 0f));
        }
    }
}
