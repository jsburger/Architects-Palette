package architectspalette.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class APConfig {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue VILLAGER_TRADES_ENABLED;
    public static ForgeConfigSpec.BooleanValue WANDERER_TRADES_ENABLED;
    public static ForgeConfigSpec.BooleanValue VERTICAL_SLABS_FORCED;

    static {

        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        BUILDER.comment("The following options require a server restart to take effect.").push("Restart_Required");
        VILLAGER_TRADES_ENABLED = BUILDER.comment("Architect's Palette adds trades to various villagers. This option controls if they can appear in newly generated trades.", "Villagers that already sell AP items will continue to do so regardless of this setting.")
                .define("enableVillagerTrades", true);
        WANDERER_TRADES_ENABLED = BUILDER.comment("Enables Wandering Trader trades added by AP.")
                .define("enableWandererTrades", true);
        VERTICAL_SLABS_FORCED = BUILDER.comment("AP adds Vertical Slabs to be compatible with Quark. Enabling this will force those to be available even if Quark isn't loaded.")
                .define("verticalSlabsForced", false);

        BUILDER.pop();
        COMMON_CONFIG = BUILDER.build();
    }


}
