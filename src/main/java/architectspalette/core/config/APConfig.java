package architectspalette.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class APConfig {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue VILLAGER_TRADES_ENABLED;
    public static ForgeConfigSpec.BooleanValue WANDERER_TRADES_ENABLED;
    public static ForgeConfigSpec.BooleanValue VERTICAL_SLABS_FORCED;
    public static ForgeConfigSpec.DoubleValue SUNSTONE_SPREAD_CHANCE;
    public static ForgeConfigSpec.BooleanValue GLOBAL_WORLDGEN_TOGGLE;
    public static ForgeConfigSpec.BooleanValue NETHER_CRYSTAL_TOGGLE;

    static {

        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        BUILDER.comment("The following options require a server restart to take effect.").push("Restart_Required");
            BUILDER.comment("World Generation Options").push("World_Gen");
            GLOBAL_WORLDGEN_TOGGLE = BUILDER.comment("Controls ALL worldgen features added by Architect's Palette.", "If false, NOTHING from AP will generate. If true, individual features may still be disabled.")
                    .define("globalWorldGenToggle", true);
            NETHER_CRYSTAL_TOGGLE = BUILDER.comment("Controls the spawning of Monazite, Ekanite, and Heliodor rods in various Nether biomes.")
                    .define("netherCrystalGeneration", true);
            BUILDER.pop();
        VILLAGER_TRADES_ENABLED = BUILDER.comment("Architect's Palette adds trades to various villagers. This option controls if they can appear in newly generated trades.", "Villagers that already sell AP items will continue to do so regardless of this setting.")
                .define("enableVillagerTrades", true);
        WANDERER_TRADES_ENABLED = BUILDER.comment("Enables Wandering Trader trades added by AP.")
                .define("enableWandererTrades", true);
        VERTICAL_SLABS_FORCED = BUILDER.comment("AP adds Vertical Slabs to be compatible with Quark. Enabling this will force those to be available even if Quark isn't loaded.")
                .define("verticalSlabsForced", false);

        BUILDER.pop();

        SUNSTONE_SPREAD_CHANCE = BUILDER.comment("Whenever Sunstone and Moonstone update their states, there is a chance for adjacent ones to update as well.",
                "This causes the updates to cascade and helps the blocks stay in sync over large areas.",
                "Default is .35, for a 35% chance of each adjacent block updating.")
                .defineInRange("sunstoneSpreadChance", 0.35, 0, 1);

        COMMON_CONFIG = BUILDER.build();
    }

    public static boolean worldGenCheck(ForgeConfigSpec.BooleanValue configOption) {
        return GLOBAL_WORLDGEN_TOGGLE.get() && configOption.get();
    }

}
