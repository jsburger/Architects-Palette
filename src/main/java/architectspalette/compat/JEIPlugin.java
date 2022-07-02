package architectspalette.compat;

import architectspalette.content.blocks.BigBrickBlock;
import architectspalette.content.blocks.CageLanternBlock;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.registry.util.StoneBlockSet;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

import static architectspalette.core.registry.APBlocks.*;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(ArchitectsPalette.MOD_ID, "jei_plugin");
    public static final RecipeType<WarpingRecipe> WARPING = RecipeType.create(ArchitectsPalette.MOD_ID, "warping", WarpingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WarpingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//        registration.addRecipeCatalyst(new ItemStack(Blocks.NETHER_PORTAL.asItem()), WarpingRecipeCategory.UID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        //Register recipes
        registration.addRecipes(WARPING, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(WarpingRecipe.TYPE));

        //Register item info
        addItemInfo(registration, CHISELED_ABYSSALINE_BRICKS, "chiseled_chargeable");
        Stream.of(ABYSSALINE, ABYSSALINE_PILLAR, ABYSSALINE_LAMP_BLOCK).
                forEach((i) -> addItemInfo(registration, i, "chargeable"));
        Stream.of(ABYSSALINE_BRICKS, ABYSSALINE_TILES).forEach((i) ->
                i.forEach(block -> addItemInfo(registration, block, "chargeable")));
        Stream.of(PLACID_ACACIA_TOTEM, GRINNING_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM)
                .forEach((i) -> addItemInfo(registration, i, "totem_carving"));
        Stream.of(FLINT_BLOCK, FLINT_PILLAR)
                .forEach((i) -> addItemInfo(registration, i, "flint_damage"));
        addItemInfo(registration, FLINT_TILES, "flint_damage");
        Stream.of(MOONSTONE, SUNSTONE)
                .forEach((i) -> addItemInfo(registration, i, "celestial_stones"));
        Stream.of(NETHER_BRASS, CUT_NETHER_BRASS, SMOOTH_NETHER_BRASS)
                .forEach((i) -> addItemInfo(registration, i, "nether_brass"));
        addItemInfo(registration, NETHER_BRASS_PILLAR, "nether_brass");
        BLOCKS.getEntries().forEach((block -> {
            if (block.get() instanceof BigBrickBlock) {
                addItemInfo(registration, block, "heavy_bricks");
            }
            else if (block.get() instanceof CageLanternBlock) {
                addItemInfo(registration, block, "cage_lanterns");
            }
        }));
    }

    private static void addItemInfo(IRecipeRegistration register, RegistryObject<? extends ItemLike> item, String infoString) {
        addItemInfo(register, item.get(), infoString);
    }

    private static void addItemInfo(IRecipeRegistration register, StoneBlockSet stoneSet, String infoString) {
        stoneSet.forEach((block -> addItemInfo(register, block, infoString)));
    }

    private static void addItemInfo(IRecipeRegistration register, ItemLike item, String infoString) {
        register.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, new TranslatableComponent(ArchitectsPalette.MOD_ID + ".info." + infoString));
    }
}
