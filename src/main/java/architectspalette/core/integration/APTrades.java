package architectspalette.core.integration;


import architectspalette.core.registry.APBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;

import static architectspalette.common.event.TradingEventHandler.add_trade;
import static net.minecraft.entity.merchant.villager.VillagerProfession.FISHERMAN;

public class APTrades {

    public static void registerTrades(){
        add_trade(FISHERMAN, 2, new BasicTrade(2, new ItemStack(APBlocks.COD_LOG.get(), 8), 6, 4));
        add_trade(FISHERMAN, 2, new BasicTrade(2, new ItemStack(APBlocks.SALMON_LOG.get(), 8), 6, 4));
    }

}
