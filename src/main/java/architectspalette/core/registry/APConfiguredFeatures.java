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

import java.util.Arrays;

public class APConfiguredFeatures {

    private static final CrystalClusterConfig HELIODOR_CLUSTER_CONFIG = new CrystalClusterConfig(1, 7, APBlocks.HELIODOR_ROD.get().defaultBlockState(), true);
    public static ConfiguredFeature<?, ?> HELIODOR_CLUSTER = APFeatures.CRYSTAL_CLUSTER.get().configured(HELIODOR_CLUSTER_CONFIG);
    public static PlacedFeature HELIODOR_CLUSTER_PLACED = HELIODOR_CLUSTER.placed(
            CountPlacement.of(7),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome());

    private static final CrystalClusterConfig EKANITE_CLUSTER_CONFIG = new CrystalClusterConfig(1, 6, APBlocks.EKANITE_ROD.get().defaultBlockState(), false);
    public static ConfiguredFeature<?, ?> EKANITE_CLUSTER = APFeatures.CRYSTAL_CLUSTER.get().configured(EKANITE_CLUSTER_CONFIG);
    public static PlacedFeature EKANITE_CLUSTER_PLACED = EKANITE_CLUSTER.placed(
            CountPlacement.of(8),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome());

    private static final CrystalClusterConfig HANGING_MONAZITE_CLUSTER_CONFIG = new CrystalClusterConfig(0, 7, APBlocks.MONAZITE_ROD.get().defaultBlockState(), true);
    public static ConfiguredFeature<?, ?> HANGING_MONAZITE_CLUSTER = APFeatures.CRYSTAL_CLUSTER.get().configured(HANGING_MONAZITE_CLUSTER_CONFIG);
    public static PlacedFeature HANGING_MONAZITE_CLUSTER_PLACED = HANGING_MONAZITE_CLUSTER.placed(
            CountPlacement.of(4),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome());

    private static final CrystalClusterConfig GROUNDED_MONAZITE_CLUSTER_CONFIG = new CrystalClusterConfig(0, 6, APBlocks.MONAZITE_ROD.get().defaultBlockState(), false);
    public static ConfiguredFeature<?, ?> GROUNDED_MONAZITE_CLUSTER = APFeatures.CRYSTAL_CLUSTER.get().configured(GROUNDED_MONAZITE_CLUSTER_CONFIG);
    public static PlacedFeature GROUNDED_MONAZITE_CLUSTER_PLACED = GROUNDED_MONAZITE_CLUSTER.placed(
            CountPlacement.of(5),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome());


    //Adds placed features to biomes
    public static void biomeLoadEvent(final BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "basalt")) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                    .add(() -> HELIODOR_CLUSTER_PLACED);
        }
        if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "warped")) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                    .add(() -> EKANITE_CLUSTER_PLACED);
        }
        if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "wastes")) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                    .add(() -> HANGING_MONAZITE_CLUSTER_PLACED);
        }
        if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "crimson")) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                    .add(() -> GROUNDED_MONAZITE_CLUSTER_PLACED);
        }
    }

    //Register configured and placed features
    public static void registerProcessedFeatures() {
        register("heliodor_cluster", HELIODOR_CLUSTER, HELIODOR_CLUSTER_PLACED);
        register("ekanite_cluster", EKANITE_CLUSTER, EKANITE_CLUSTER_PLACED);
        register("hanging_monazite_cluster", HANGING_MONAZITE_CLUSTER, HANGING_MONAZITE_CLUSTER_PLACED);
        register("grounded_monazite_cluster", GROUNDED_MONAZITE_CLUSTER, GROUNDED_MONAZITE_CLUSTER_PLACED);
    }

    private static void register(String name, ConfiguredFeature<?, ?> configuredFeature, PlacedFeature placedFeature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ArchitectsPalette.MOD_ID, name), configuredFeature);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ArchitectsPalette.MOD_ID, name), placedFeature);
    }

    private static boolean biomeHasName(BiomeLoadingEvent biome, String... names) {
        if (biome.getName() != null) {
            String biomeName = biome.getName().toString().toLowerCase();
            return Arrays.stream(names).anyMatch(biomeName::contains);
        }
        return false;
    }
}
