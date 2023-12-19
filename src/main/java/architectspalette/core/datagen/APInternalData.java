package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class APInternalData {

    public static RegistrySetBuilder getRegistrySetBuilder() {
        var builder = new RegistrySetBuilder();
        populateSet(builder);
        return builder;
    }

    static void populateSet(RegistrySetBuilder set) {
        // Feature wrappers
        var wrappers = APFeatures.getFeatureWrappers();
        set.add(Registries.CONFIGURED_FEATURE, context -> {
            wrappers.forEach(feature -> {
                context.register(
                        ResourceKey.create(Registries.CONFIGURED_FEATURE, ArchitectsPalette.rl(feature.name())),
                        feature.configuredFeature()
                );
            });
            context.register(
                    ResourceKey.create(Registries.CONFIGURED_FEATURE, ArchitectsPalette.rl("twisted_tree")),
                    new ConfiguredFeature<>(Feature.TREE,
                            new TreeConfiguration.TreeConfigurationBuilder(
                                    BlockStateProvider.simple(APBlocks.TWISTED_LOG.get().defaultBlockState()),
                                    new ForkingTrunkPlacer(5, 2, 2),
                                    BlockStateProvider.simple(APBlocks.TWISTED_LEAVES.get().defaultBlockState()),
                                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                                    new TwoLayersFeatureSize(1, 0, 2)
                            )
                                    .ignoreVines()
                                    .build()
                    )
            );
        });
        set.add(Registries.PLACED_FEATURE, context -> {
            wrappers.forEach(feature -> {
                context.register(
                        ResourceKey.create(Registries.PLACED_FEATURE, ArchitectsPalette.rl(feature.name() + "_placed")),
                        new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ResourceKey.create(Registries.CONFIGURED_FEATURE, ArchitectsPalette.rl(feature.name()))), feature.placement())
                );
            });
        });
    }
}
