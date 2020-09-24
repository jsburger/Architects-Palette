package architectspalette.common.event;

import architectspalette.core.ArchitectsPalette;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID)
public class TradingEventHandler {

     //this sucks, please let me know how to make it better, thanks
     //To break it down a little
     // this + Profession -> Map of trades for that profession, Map + Trade level -> List of trades of that level
    private static final Map<VillagerProfession, Map<Integer, LinkedList<VillagerTrades.ITrade>>> professionMap = new HashMap<>();

    public static void add_trade(VillagerProfession prof, Integer level, VillagerTrades.ITrade trade) {
        professionMap.putIfAbsent(prof, new HashMap<>());
        professionMap.get(prof).putIfAbsent(level, new LinkedList<>());
        professionMap.get(prof).get(level).add(trade);
    }

     // i think i can use this to save memory, idk tho
    private static void clear_map() {
        professionMap.clear();
    }

    @SubscribeEvent
    public static void onTradesLoaded (VillagerTradesEvent event) {
        VillagerProfession profession = event.getType();
        Map<Integer, LinkedList<VillagerTrades.ITrade>> prof_trades = professionMap.get(profession);
        if (prof_trades != null) {
            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
            trades.forEach((level, list) -> {
                    if (prof_trades.containsKey(level)) {
                        list.addAll(prof_trades.get(level));
                    }
                }
            );
//            trades.putAll(professionMap.get(profession));
        }
//
//        if (event.getType() == VillagerProfession.FISHERMAN) {
//            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
//            trades.get(1).add(new BasicTrade(2, new ItemStack(APBlocks.COD_LOG.get(), 8), 5, 2));
//        }
    }
}
