package architectspalette.core;

import architectspalette.core.config.APConfig;
import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.integration.APCriterion;
import architectspalette.core.integration.APTrades;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.loot.WitheredBoneLootModifier;
import architectspalette.core.registry.*;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(value = ArchitectsPalette.MOD_ID)
public class ArchitectsPalette {
    public static final String MOD_ID = "architects_palette";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ArchitectsPalette instance;

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public ArchitectsPalette() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);

        MiscRegistry.PARTICLE_TYPES.register(modEventBus);
        APSounds.SOUNDS.register(modEventBus);
        APBlocks.BLOCKS.register(modEventBus);
        APItems.ITEMS.register(modEventBus);
        APFeatures.FEATURES.register(modEventBus);
//        APTileEntities.TILE_ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
        modEventBus.addListener(EventPriority.LOWEST, this::setupClient);

        registerRecipeSerializers(modEventBus);
        registerLootSerializers(modEventBus);
        // Biomes need to be registered before features.
        registerBiomeSerializers(modEventBus);
        registerPlacedFeatures(modEventBus);

        CraftingHelper.register(new APVerticalSlabsCondition.Serializer());

//        AtomicInteger size = new AtomicInteger(APBlocks.BLOCKS.getEntries().size());
//        StoneBlockSet.forAllSets((set -> {
//            set.forEachRegistryObject(a ->
//                    size.addAndGet(-1)
//            );
//            size.addAndGet(1);
//        }));
//        LOGGER.debug("Block Count: " + size.get());

        MinecraftForge.EVENT_BUS.register(this);
    }

    void setupCommon(final FMLCommonSetupEvent event) {

        APBlockProperties.registerFlammables();
        APTrades.registerTrades();

        // Is this okay to go here?
        APCriterion.register();
    }

    void registerRecipeSerializers(IEventBus bus) {
        //Register the recipe type
        DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ArchitectsPalette.MOD_ID);
        RegistryObject<RecipeType<WarpingRecipe>> WARPING = RECIPE_TYPES.register(WarpingRecipe.TYPE.toString(), () -> new RecipeType<WarpingRecipe>() {});

        //Register the serializer
        DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArchitectsPalette.MOD_ID);
        RegistryObject<WarpingRecipe.Serializer> WARPING_S = RECIPE_SERIALIZERS.register(WarpingRecipe.TYPE.toString(), () -> WarpingRecipe.SERIALIZER);

        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
    }

    void registerLootSerializers(IEventBus bus) {
        DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ArchitectsPalette.MOD_ID);
        RegistryObject<Codec<WitheredBoneLootModifier>> WITHER_SKELETON_DROPS = LOOT.register("wither_skeleton_bones", WitheredBoneLootModifier.CODEC);

        LOOT.register(bus);
    }

    void registerBiomeSerializers(IEventBus bus) {
        APBiomeModifiers.BIOME_MODIFIER_SERIALIZER.register(bus);
    }

    void registerPlacedFeatures(IEventBus bus) {
        APPlacedFeatures.PLACED_FEATURES.register(bus);
    }

    void setupClient(final FMLClientSetupEvent event) {
        APBlockProperties.setupRenderLayers();
    }
}
