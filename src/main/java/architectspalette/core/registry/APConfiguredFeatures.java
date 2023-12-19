package architectspalette.core.registry;

public class APConfiguredFeatures {
    //Configs and Features have to be suppliers, otherwise they reference blocks before they are registered.
    /*public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> TWISTED_TREE =
        FeatureUtils.register("twisted_tree", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(APBlocks.TWISTED_LOG.get().defaultBlockState()),
                    new ForkingTrunkPlacer(5, 2, 2),
                    BlockStateProvider.simple(APBlocks.TWISTED_LEAVES.get().defaultBlockState()),
                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2)
            )
            .ignoreVines()
            .build()
        );
    /*
    public static final Supplier<CrystalClusterConfig> HELIODOR_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(1, 7, APBlocks.HELIODOR_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> HELIODOR_CLUSTER =
            ForgeRegistries.FEATURES.register("heliodor_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), HELIODOR_CLUSTER_CONFIG.get()));

    private static final Supplier<CrystalClusterConfig> EKANITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(1, 6, APBlocks.EKANITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> EKANITE_CLUSTER =
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, "ekanite_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), EKANITE_CLUSTER_CONFIG.get()));

    private static final Supplier<CrystalClusterConfig> HANGING_MONAZITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(0, 7, APBlocks.MONAZITE_ROD.get().defaultBlockState(), true, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> HANGING_MONAZITE_CLUSTER =
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, "hanging_monazite_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), HANGING_MONAZITE_CLUSTER_CONFIG.get()));

    private static final Supplier<CrystalClusterConfig> GROUNDED_MONAZITE_CLUSTER_CONFIG = () ->
            new CrystalClusterConfig(0, 6, APBlocks.MONAZITE_ROD.get().defaultBlockState(), false, Blocks.BASALT.defaultBlockState());
    public static final Holder<ConfiguredFeature<?, ?>> GROUNDED_MONAZITE_CLUSTER =
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, "grounded_monazite_cluster", new ConfiguredFeature(APFeatures.CRYSTAL_CLUSTER.get(), GROUNDED_MONAZITE_CLUSTER_CONFIG.get()));
     */
}
