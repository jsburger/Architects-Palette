package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.config.APConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class APBiomeModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ArchitectsPalette.MOD_ID);

    public static RegistryObject<Codec<APUndergroundDecorationBiomeModifier>> UNDERGROUND_DECORATION_MODIFIER = BIOME_MODIFIER_SERIALIZER.register("underground_decoration", () ->
        RecordCodecBuilder.create(builder -> builder.group(
                Biome.LIST_CODEC.fieldOf("biomes").forGetter(APUndergroundDecorationBiomeModifier::biomes),
                PlacedFeature.CODEC.fieldOf("feature").forGetter(APUndergroundDecorationBiomeModifier::feature),
                GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(APUndergroundDecorationBiomeModifier::step)
        ).apply(builder, APUndergroundDecorationBiomeModifier::new)));

    public record APUndergroundDecorationBiomeModifier(
            HolderSet<Biome> biomes,
            Holder<PlacedFeature> feature,
            GenerationStep.Decoration step
    ) implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD && biomes.contains(biome) && APConfig.worldGenCheck(APConfig.NETHER_CRYSTAL_TOGGLE)) {
                builder.getGenerationSettings().addFeature(this.step, feature);
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return UNDERGROUND_DECORATION_MODIFIER.get();
        }
    }
}
