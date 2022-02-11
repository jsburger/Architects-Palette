package architectspalette.core.registry;

import architectspalette.content.worldgen.features.CrystalClusterFeature;
import architectspalette.content.worldgen.features.configs.CrystalClusterConfig;
import architectspalette.core.ArchitectsPalette;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class APFeatures {

    //APConfiguredFeatures is probably where you wanna be. This place doesn't do much.

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ArchitectsPalette.MOD_ID);

    public static final RegistryObject<Feature<CrystalClusterConfig>> CRYSTAL_CLUSTER = FEATURES.register("crystal_cluster", () -> new CrystalClusterFeature(CrystalClusterConfig.CODEC));



}
