package architectspalette.core.integration;


import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;

import static architectspalette.common.event.TradingEventHandler.add_trade;
import static net.minecraft.entity.merchant.villager.VillagerProfession.*;

public class APTrades {

    public static void registerTrades() {
        if (APConfig.VILLAGER_TRADES_ENABLED.get()) {
            // Fish Blocks
            add_trade(FISHERMAN, 2, new BasicTrade(2, new ItemStack(APBlocks.COD_LOG.get(), 8), 6, 4, 0.05f));
            add_trade(FISHERMAN, 2, new BasicTrade(2, new ItemStack(APBlocks.SALMON_LOG.get(), 8), 6, 4, 0.05f));
            // Entrails
            add_trade(BUTCHER, 2, new BasicTrade(1, new ItemStack(APBlocks.ENTRAILS.get(), 5), 5, 4, 0.0f));
            // Plating
            add_trade(ARMORER, 2, new BasicTrade(3, new ItemStack(APBlocks.PLATING_BLOCK.get(), 12), 6, 4, 0.1F));
            // Pipes
            add_trade(TOOLSMITH, 2, new BasicTrade(4, new ItemStack(APBlocks.PIPE.get(), 12), 6, 4, 0.1F));
            // Spools
            add_trade(SHEPHERD, 2, new BasicTrade(1, new ItemStack(APBlocks.SPOOL.get(), 2), 5, 4, 0.0F));

            // Temporary survival recipes until properly implemented
            add_trade(MASON, 1, new BasicTrade(1, new ItemStack(APBlocks.LIMESTONE.get(), 16), 5, 3, 0.05f));
            add_trade(MASON, 1, new BasicTrade(1, new ItemStack(APBlocks.OLIVESTONE_BRICK.get(), 16), 5, 3, 0.05f));
        }
    }

}
