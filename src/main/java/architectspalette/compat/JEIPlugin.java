package architectspalette.compat;

import architectspalette.content.blocks.BigBrickBlock;
import architectspalette.content.blocks.CageLanternBlock;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
        builder()
                .add(CHISELED_ABYSSALINE_BRICKS, CHISELED_HADALINE_BRICKS)
                .registerInfo(registration, "chiseled_chargeable");
        builder()
                .add(ABYSSALINE, ABYSSALINE_PILLAR, ABYSSALINE_LAMP_BLOCK, ABYSSALINE_PLATING)
                .add(ABYSSALINE_BRICKS, ABYSSALINE_TILES)
                .add(HADALINE, HADALINE_PILLAR, HADALINE_LAMP_BLOCK, HADALINE_PLATING)
                .add(HADALINE_BRICKS, HADALINE_TILES)
                .registerInfo(registration, "chargeable");
        builder()
                .add(PLACID_ACACIA_TOTEM, GRINNING_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM)
                .registerInfo(registration, "totem_carving");
        builder()
                .add(FLINT_BLOCK, FLINT_PILLAR)
                .add(FLINT_TILES)
                .registerInfo(registration, "flint_damage");
        builder()
                .add(MOONSTONE, SUNSTONE)
                .registerInfo(registration, "celestial_stones");
        builder()
                .add(NETHER_BRASS, CUT_NETHER_BRASS, SMOOTH_NETHER_BRASS)
                .add(NETHER_BRASS_PILLAR)
                .registerInfo(registration, "nether_brass");
        builder()
                .add(block -> block instanceof BigBrickBlock)
                .registerInfo(registration, "heavy_bricks");
        builder()
                .add(block -> block instanceof CageLanternBlock)
                .registerInfo(registration, "cage_lanterns");
        builder()
                .add(WARDSTONE, WARDSTONE_BRICKS)
                .add(WARDSTONE_PILLAR, CHISELED_WARDSTONE, WARDSTONE_LAMP)
                .registerInfo(registration, "wardstone");
    }

    private static void addItemInfo(IRecipeRegistration register, RegistryObject<? extends ItemLike> item, String infoString) {
        addItemInfo(register, item.get(), infoString);
    }

    private static void addItemInfo(IRecipeRegistration register, StoneBlockSet stoneSet, String infoString) {
        stoneSet.forEach((block -> addItemInfo(register, block, infoString)));
    }

    private static void addItemInfo(IRecipeRegistration register, ItemLike item, String infoString) {
        register.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, Component.translatable(ArchitectsPalette.MOD_ID + ".info." + infoString));
    }


    private static BlockListBuilder builder() {
        return new BlockListBuilder();
    }
    private static class BlockListBuilder {
        private final List<Block> blocks = new ArrayList<>();
        private BlockListBuilder add(BlockNode... nodes) {
            for (BlockNode node : nodes)
                node.forEach((n) -> blocks.add(n.get()));
            return this;
        }
        private BlockListBuilder add(StoneBlockSet... sets) {
            for (StoneBlockSet set : sets) {
                set.forEach(blocks::add);
            }
            return this;
        }
        private BlockListBuilder add(Predicate<Block> filter) {
            for (RegistryObject<Block> entry : BLOCKS.getEntries()) {
                if (filter.test(entry.get())) blocks.add(entry.get());
            }
            return this;
        }
        @SafeVarargs
        private BlockListBuilder add(RegistryObject<? extends Block>... blockList) {
            for (RegistryObject<? extends Block> block : blockList) {
                blocks.add(block.get());
            };
            return this;
        }
        private void registerInfo(IRecipeRegistration register, String infoString) {
            register.addIngredientInfo(blocks.stream().map(ItemStack::new).toList(), VanillaTypes.ITEM_STACK, Component.translatable(ArchitectsPalette.MOD_ID + ".info." + infoString));
            blocks.clear();
        }
    }
}
