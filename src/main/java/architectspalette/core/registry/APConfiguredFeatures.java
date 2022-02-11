package architectspalette.core.registry;

import architectspalette.content.worldgen.features.configs.CrystalClusterConfig;
import architectspalette.core.ArchitectsPalette;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class APConfiguredFeatures {

    private static final CrystalClusterConfig HELIODOR_CLUSTER_CONFIG = new CrystalClusterConfig(1, 7, APBlocks.HELIODOR_ROD.get().defaultBlockState());
    public static ConfiguredFeature<?, ?> HELIODOR_CLUSTER = APFeatures.CRYSTAL_CLUSTER.get().configured(HELIODOR_CLUSTER_CONFIG);
    public static PlacedFeature HELIODOR_CLUSTER_PLACED = HELIODOR_CLUSTER.placed(
            CountPlacement.of(10),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome());




    //Adds placed features to biomes
    public static void biomeLoadEvent(final BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                    .add(() -> HELIODOR_CLUSTER_PLACED);
        }
    }



    public static void registerProcessedFeatures() {
        register("heliodor_cluster", HELIODOR_CLUSTER, HELIODOR_CLUSTER_PLACED);
    }

    private static void register(String name, ConfiguredFeature<?, ?> configuredFeature, PlacedFeature placedFeature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ArchitectsPalette.MOD_ID, name), configuredFeature);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ArchitectsPalette.MOD_ID, name), placedFeature);
    }
}
