package architectspalette.core.registry.util;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * Used to generate JSON files for implementing features.
 * Only exists during data generation.
 */
public final class FeatureWrapper<T extends FeatureConfiguration> {
    private final String name;
    private final RegistryObject<Feature<T>> feature;
    private final T config;
    private final List<PlacementModifier> placement;

    public FeatureWrapper(String name, RegistryObject<Feature<T>> feature, T config, List<PlacementModifier> placement) {
        this.name = name;
        this.feature = feature;
        this.config = config;
        this.placement = placement;
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

}
