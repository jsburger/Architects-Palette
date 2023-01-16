package architectspalette.core.registry;

import architectspalette.content.worldgen.features.CrystalClusterFeature;
import architectspalette.content.worldgen.features.configs.CrystalClusterConfig;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.config.APConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class APConfiguredFeatures {
    //Configs and Features have to be suppliers, otherwise they reference blocks before they are registered.
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> TWISTED_TREE =
        FeatureUtils.register("twisted_tree", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(APBlocks.TWISTED_LOG.get().defaultBlockState()),
                    new ForkingTrunkPlacer(5, 2, 2),
                    BlockStateProvider.simple(APBlocks.TWISTED_LEAVES.get().defaultBlockState()),
                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2)
            )
            .ignoreVines()
            .build()
        );
    public static final Holder<PlacedFeature> TWISTED_TREE_CHECKED = PlacementUtils.register("twisted_tree_checked", TWISTED_TREE,
                    PlacementUtils.filteredByBlockSurvival(APBlocks.TWISTED_SAPLING.get()));
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TWISTED_TREE_SPAWN =
            FeatureUtils.register("twisted_tree_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TWISTED_TREE_CHECKED, 0.5F)), TWISTED_TREE_CHECKED));
    private static final Supplier<CrystalClusterConfig> HELIODOR_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(1, 7, APBlocks.HELIODOR_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> HELIODOR_CLUSTER =
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, "heliodor_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), HELIODOR_CLUSTER_CONFIG.get()));

    private static final Supplier<CrystalClusterConfig> EKANITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(1, 6, APBlocks.EKANITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> EKANITE_CLUSTER =
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, "ekanite_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), EKANITE_CLUSTER_CONFIG.get()));

    private static final Supplier<CrystalClusterConfig> HANGING_MONAZITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(0, 7, APBlocks.MONAZITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> HANGING_MONAZITE_CLUSTER =
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, "hanging_monazite_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), HANGING_MONAZITE_CLUSTER_CONFIG.get()));

    private static final Supplier<CrystalClusterConfig> GROUNDED_MONAZITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(0, 6, APBlocks.MONAZITE_ROD.get().defaultBlockState(), false, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> GROUNDED_MONAZITE_CLUSTER =
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, "grounded_monazite_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), GROUNDED_MONAZITE_CLUSTER_CONFIG.get()));
}
