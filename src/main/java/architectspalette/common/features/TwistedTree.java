package architectspalette.common.features;

import architectspalette.core.registry.APBlocks;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class TwistedTree extends AbstractTreeGrower {

    public static final LazyLoadedValue<TreeConfiguration> TWISTED_TREE_CONFIG = new LazyLoadedValue<>(() ->
            new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(APBlocks.TWISTED_LOG.get().defaultBlockState()),
                    new ForkingTrunkPlacer(5, 2, 2),
                    new SimpleStateProvider(APBlocks.TWISTED_LEAVES.get().defaultBlockState()),
                    new SimpleStateProvider(APBlocks.TWISTED_SAPLING.get().defaultBlockState()),
                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2)
            )
            .ignoreVines()
            .build()
    );


    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
        return Feature.TREE.configured(TWISTED_TREE_CONFIG.get());
    }

}
