package architectspalette.content.worldgen.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class CrystalClusterConfig implements FeatureConfiguration {
    public static final Codec<CrystalClusterConfig> CODEC = RecordCodecBuilder.<CrystalClusterConfig>create(
            (configInstance) ->
                configInstance.group(
                        Codec.intRange(0, 100)
                                .fieldOf("min_length")
                                .forGetter(config -> config.minLength),
                        Codec.intRange(0, 100)
                                .fieldOf("max_length")
                                .forGetter(config -> config.maxLength),
                        BlockState.CODEC
                                .fieldOf("crystal_state")
                                .forGetter(config -> config.crystalState),
                        Codec.BOOL
                                .fieldOf("hanging")
                                .forGetter(config -> config.hanging),
                        BlockState.CODEC
                                .fieldOf("extrusion_state")
                                .forGetter(config -> config.extrusionState)
                ).apply(configInstance, CrystalClusterConfig::new)
    );

    public final int minLength;
    public final int maxLength;
    public final BlockState crystalState;
    public final boolean hanging;
    public final BlockState extrusionState;

    public CrystalClusterConfig(int minLength, int maxLength, BlockState crystalState, Boolean hanging, BlockState extrusion) {

        this.minLength = minLength;
        this.maxLength = maxLength;
        this.crystalState = crystalState;
        this.hanging = hanging;
        this.extrusionState = extrusion;
    }
}
