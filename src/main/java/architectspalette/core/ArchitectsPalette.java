package architectspalette.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = ArchitectsPalette.MOD_ID)
public class ArchitectsPalette {
	public static final String MOD_ID = "architects_palette";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID.toUpperCase());
	public static ArchitectsPalette instance;
	
	public ArchitectsPalette() {
		instance = this;
		
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
	}
	
	void setupCommon(final FMLCommonSetupEvent event) {
		
	}
}