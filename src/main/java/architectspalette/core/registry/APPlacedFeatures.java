package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class APPlacedFeatures {
    // Features have 3 levels of abstraction.
    // Feature: the thing that does the generation.
    // ConfiguredFeature: Feature + Configuration, e.g. how big it is or which BlockStates, etc.
    // PlacedFeature: ConfiguredFeature + placement options, e.g. diamonds go at the bottom of the world
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
        DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ArchitectsPalette.MOD_ID);

    public static final RegistryObject<PlacedFeature> TWISTED_TREE_PLACED = PLACED_FEATURES.register("twisted_tree_placed",
        () -> new PlacedFeature(
            (Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)APConfiguredFeatures.TWISTED_TREE_SPAWN,
            VegetationPlacements.treePlacement(
                RarityFilter.onAverageOnceEvery(32)
                // Probably change that...
            )
        )
    );
    public static final RegistryObject<PlacedFeature> HELIODOR_CLUSTER_PLACED = PLACED_FEATURES.register("heliodor_cluster_placed",
        () -> new PlacedFeature(
            (Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)APConfiguredFeatures.HELIODOR_CLUSTER,
            List.of(
                CountPlacement.of(7),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome()
            )
        )
    );
    public static final RegistryObject<PlacedFeature> EKANITE_CLUSTER_PLACED = PLACED_FEATURES.register("ekanite_cluster_placed",
        () -> new PlacedFeature(
            (Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)APConfiguredFeatures.EKANITE_CLUSTER,
            List.of(
                CountPlacement.of(8),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome()
            )
        )
    );
    public static final RegistryObject<PlacedFeature> HANGING_MONAZITE_CLUSTER_PLACED = PLACED_FEATURES.register("hanging_monazite_cluster_placed",
        () -> new PlacedFeature(
            (Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)APConfiguredFeatures.HANGING_MONAZITE_CLUSTER,
            List.of(
                CountPlacement.of(4),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome()
            )
        )
    );
    public static final RegistryObject<PlacedFeature> GROUNDED_MONAZITE_CLUSTER_PLACED = PLACED_FEATURES.register("grounded_monazite_cluster_placed",
        () -> new PlacedFeature(
            (Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)APConfiguredFeatures.GROUNDED_MONAZITE_CLUSTER,
            List.of(
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome()
            )
        )
    );
}
