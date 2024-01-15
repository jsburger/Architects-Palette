package architectspalette.core.registry;

import architectspalette.content.worldgen.features.CrystalClusterFeature;
import architectspalette.content.worldgen.features.configs.CrystalClusterConfig;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.FeatureWrapper;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class APFeatures {
    // Features have 3 levels of abstraction.
    // Feature: the thing that does the generation.
    // ConfiguredFeature: Feature + Configuration, e.g. how big it is or which BlockStates, etc.
    // PlacedFeature: ConfiguredFeature + placement options, e.g. diamonds go at the bottom of the world

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ArchitectsPalette.MOD_ID);

    public static final RegistryObject<Feature<CrystalClusterConfig>> CRYSTAL_CLUSTER = FEATURES.register("crystal_cluster", () -> new CrystalClusterFeature(CrystalClusterConfig.CODEC));

    // Configured and Placed features are completely data-driven now, so this function is only ever called during datagen.
    // However, this can effectively be treated as registration.
    public static List<FeatureWrapper<?>> getFeatureWrappers() {
        return List.of(
                new FeatureWrapper<>("heliodor_cluster",
                        CRYSTAL_CLUSTER,
                        new CrystalClusterConfig(1, 7, APBlocks.HELIODOR_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState()),
                        List.of(
                                CountPlacement.of(7),
                                InSquarePlacement.spread(),
                                PlacementUtils.FULL_RANGE,
                                BiomeFilter.biome()
                        )
                ),
                new FeatureWrapper<>("ekanite_cluster",
                        CRYSTAL_CLUSTER,
                        new CrystalClusterConfig(1, 6, APBlocks.EKANITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState()),
                        List.of(
                                CountPlacement.of(8),
                                InSquarePlacement.spread(),
                                PlacementUtils.FULL_RANGE,
                                BiomeFilter.biome()
                        )
                ),
                new FeatureWrapper<>("hanging_monazite_cluster",
                        CRYSTAL_CLUSTER,
                        new CrystalClusterConfig(0, 7, APBlocks.MONAZITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState()),
                        List.of(
                                CountPlacement.of(4),
                                InSquarePlacement.spread(),
                                PlacementUtils.FULL_RANGE,
                                BiomeFilter.biome()
                        )
                ),
                new FeatureWrapper<>( "monazite_cluster",
                        CRYSTAL_CLUSTER,
                        new CrystalClusterConfig(0, 6, APBlocks.MONAZITE_ROD.get().defaultBlockState(), false, Blocks.BASALT.defaultBlockState()),
                        List.of(
                                CountPlacement.of(5),
                                InSquarePlacement.spread(),
                                PlacementUtils.FULL_RANGE,
                                BiomeFilter.biome()
                        )
                )

        );

    }



}
