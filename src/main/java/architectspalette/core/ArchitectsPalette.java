package architectspalette.core;

import architectspalette.core.integration.APBlockData;
import architectspalette.core.integration.APTrades;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.APSounds;
import architectspalette.core.registry.APTileEntities;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = ArchitectsPalette.MOD_ID)
public class ArchitectsPalette {
    public static final String MOD_ID = "architects_palette";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID.toUpperCase());
    public static ArchitectsPalette instance;

    public ArchitectsPalette() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        APSounds.SOUNDS.register(modEventBus);
        APBlocks.BLOCKS.register(modEventBus);
        APItems.ITEMS.register(modEventBus);
        APTileEntities.TILE_ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
        modEventBus.addListener(EventPriority.NORMAL, this::setupClient);
    }

    void setupCommon(final FMLCommonSetupEvent event) {
        APBlockData.registerFlammables();
        APTrades.registerTrades();
    }

    void setupClient(final FMLClientSetupEvent event) {
        APBlockData.setupRenderLayers();
    }
}