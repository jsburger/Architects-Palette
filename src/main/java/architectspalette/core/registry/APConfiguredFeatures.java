package architectspalette.core.registry;

import architectspalette.content.worldgen.features.configs.CrystalClusterConfig;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.config.APConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class APConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ArchitectsPalette.MOD_ID);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ArchitectsPalette.MOD_ID);

    //Configs and Features have to be suppliers, otherwise they reference blocks before they are registered.
    private static <FC extends FeatureConfiguration> RegistryObject<ConfiguredFeature<?, ?>> registerConfigured(String name, Supplier<Feature<FC>> feature, Supplier<FC> configSupplier) {
        return CONFIGURED_FEATURES.register(name, () ->
                new ConfiguredFeature<>(feature.get(), configSupplier.get())
        );
    }
    private static RegistryObject<PlacedFeature> registerPlaced(String name, RegistryObject<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placements) {
        return PLACED_FEATURES.register(name, () -> new PlacedFeature(configuredFeature.getHolder().get(), List.of(placements)));
    }

    private static final Supplier<TreeConfiguration> TWISTED_TREE_CONFIG = () ->
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(APBlocks.TWISTED_LOG.get().defaultBlockState()),
                    new ForkingTrunkPlacer(5, 2, 2),
                    BlockStateProvider.simple(APBlocks.TWISTED_LEAVES.get().defaultBlockState()),
                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2)
            )
            .ignoreVines()
            .build();

    public static final RegistryObject<ConfiguredFeature<?, ?>> TWISTED_TREE = registerConfigured("twisted_tree", ()->Feature.TREE, TWISTED_TREE_CONFIG);

    private static final Supplier<CrystalClusterConfig> HELIODOR_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(1, 7, APBlocks.HELIODOR_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());

    public static final RegistryObject<ConfiguredFeature<?, ?>> HELIODOR_CLUSTER =
            registerConfigured("heliodor_cluster", APFeatures.CRYSTAL_CLUSTER, HELIODOR_CLUSTER_CONFIG);
    public static final RegistryObject<PlacedFeature> HELIODOR_CLUSTER_PLACED = registerPlaced("heliodor_cluster", HELIODOR_CLUSTER,
            CountPlacement.of(7),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome()
    );

    private static final Supplier<CrystalClusterConfig> EKANITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(1, 6, APBlocks.EKANITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());

    public static final RegistryObject<ConfiguredFeature<?, ?>> EKANITE_CLUSTER =
            registerConfigured("ekanite_cluster", APFeatures.CRYSTAL_CLUSTER, EKANITE_CLUSTER_CONFIG);
    public static final RegistryObject<PlacedFeature> EKANITE_CLUSTER_PLACED = registerPlaced("ekanite_cluster", EKANITE_CLUSTER,
            CountPlacement.of(8),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome()
    );

    private static final Supplier<CrystalClusterConfig> HANGING_MONAZITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(0, 7, APBlocks.MONAZITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());

    public static final RegistryObject<ConfiguredFeature<?, ?>> HANGING_MONAZITE_CLUSTER =
            registerConfigured("hanging_monazite_cluster", APFeatures.CRYSTAL_CLUSTER, HANGING_MONAZITE_CLUSTER_CONFIG);
    public static final RegistryObject<PlacedFeature> HANGING_MONAZITE_CLUSTER_PLACED = registerPlaced("hanging_monazite_cluster", HANGING_MONAZITE_CLUSTER,
            CountPlacement.of(4),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome()
    );

    private static final Supplier<CrystalClusterConfig> GROUNDED_MONAZITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(0, 6, APBlocks.MONAZITE_ROD.get().defaultBlockState(), false, Blocks.BASALT.defaultBlockState());

    public static final RegistryObject<ConfiguredFeature<?, ?>> GROUNDED_MONAZITE_CLUSTER =
            registerConfigured("grounded_monazite_cluster", APFeatures.CRYSTAL_CLUSTER, GROUNDED_MONAZITE_CLUSTER_CONFIG);
    public static final RegistryObject<PlacedFeature> GROUNDED_MONAZITE_CLUSTER_PLACED = registerPlaced("grounded_monazite_cluster", GROUNDED_MONAZITE_CLUSTER,
            CountPlacement.of(5),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BiomeFilter.biome()
    );


    //Adds placed features to biomes
    public static void biomeLoadEvent(final BiomeLoadingEvent event) {
        //Add Nether Crystals
        if (APConfig.worldGenCheck(APConfig.NETHER_CRYSTAL_TOGGLE)) {
            Biome.BiomeCategory category = event.getCategory();
            if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "basalt")) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                        .add(HELIODOR_CLUSTER_PLACED.getHolder().get());
            }
            if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "warped")) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                        .add(EKANITE_CLUSTER_PLACED.getHolder().get());
            }
            if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "wastes")) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                        .add(HANGING_MONAZITE_CLUSTER_PLACED.getHolder().get());
            }
            if (category.equals(Biome.BiomeCategory.NETHER) && biomeHasName(event, "crimson")) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                        .add(GROUNDED_MONAZITE_CLUSTER_PLACED.getHolder().get());
            }
        }
    }


    private static boolean biomeHasName(BiomeLoadingEvent biome, String... names) {
        if (biome.getName() != null) {
            String biomeName = biome.getName().toString().toLowerCase();
            return Arrays.stream(names).anyMatch(biomeName::contains);
        }
        return false;
    }
}
