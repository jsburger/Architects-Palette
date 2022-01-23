package architectspalette.common.features;

import architectspalette.core.registry.APBlocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.LazyValue;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class TwistedTree extends Tree {

    public static final LazyValue<BaseTreeFeatureConfig> TWISTED_TREE_CONFIG = new LazyValue<>(() ->
            new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(APBlocks.TWISTED_LOG.get().getDefaultState()),
                    new SimpleBlockStateProvider(APBlocks.TWISTED_LEAVES.get().getDefaultState()),
                    new AcaciaFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0)),
                    new ForkyTrunkPlacer(5, 2, 2),
                    new TwoLayerFeature(1, 0, 2)
            )
            .setIgnoreVines()
            .build()
    );


    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return Feature.TREE.withConfiguration(TWISTED_TREE_CONFIG.getValue());
    }

}
