package architectspalette.core;

import architectspalette.core.config.APConfig;
import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.datagen.GatherData;
import architectspalette.core.integration.APCriterion;
import architectspalette.core.integration.APTrades;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.loot.WitheredBoneLootModifier;
import architectspalette.core.registry.*;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = ArchitectsPalette.MOD_ID)
public class ArchitectsPalette {
    public static final String MOD_ID = "architects_palette";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID.toUpperCase());
    public static ArchitectsPalette instance;

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
        APConfiguredFeatures.CONFIGURED_FEATURES.register(modEventBus);
        APConfiguredFeatures.PLACED_FEATURES.register(modEventBus);
//        APTileEntities.TILE_ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
        modEventBus.addListener(EventPriority.LOWEST, this::setupClient);
        registerRecipeSerializers(modEventBus);
        registerLootSerializers(modEventBus);

        forgeBus.addListener(APConfiguredFeatures::biomeLoadEvent);

        CraftingHelper.register(new APVerticalSlabsCondition.Serializer());

        GatherData.load();

    }

    void setupCommon(final FMLCommonSetupEvent event) {

        APBlockProperties.registerFlammables();
        APTrades.registerTrades();

        // Is this okay to go here?
        APCriterion.register();
    }

    void registerRecipeSerializers(IEventBus bus) {
        //Register the recipe type
        DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ArchitectsPalette.MOD_ID);
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

    void setupClient(final FMLClientSetupEvent event) {
        APBlockProperties.setupRenderLayers();
    }
}
