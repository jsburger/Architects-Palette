package architectspalette.core.registry.util;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.RegistryObject;
import oshi.util.Memoizer;

import java.util.List;
import java.util.function.Supplier;

/**
 * Used to generate JSON files for implementing features.
 * Only exists during data generation.
 */
public final class FeatureWrapper<T extends FeatureConfiguration> {
    private final String name;
    private final RegistryObject<Feature<T>> feature;
    private final T config;
    private final List<PlacementModifier> placement;
    private final Supplier<ConfiguredFeature<T, Feature<T>>> configuredFeatureSupplier;

    public FeatureWrapper(String name, RegistryObject<Feature<T>> feature, T config, List<PlacementModifier> placement) {
        this.name = name;
        this.feature = feature;
        this.config = config;
        this.placement = placement;
        configuredFeatureSupplier = Memoizer.memoize(() -> new ConfiguredFeature<>(feature().get(), config));
    }

    public String name() {
        return name;
    }

    public RegistryObject<Feature<T>> feature() {
        return feature;
    }

    public T config() {
        return config;
    }

    public List<PlacementModifier> placement() {
        return placement;
    }

    public ConfiguredFeature<T, Feature<T>> configuredFeature() {
        return configuredFeatureSupplier.get();
    }

}
