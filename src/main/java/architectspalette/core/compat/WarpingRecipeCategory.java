package architectspalette.core.compat;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.registry.APBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WarpingRecipeCategory implements IRecipeCategory<WarpingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(ArchitectsPalette.MOD_ID, "warping");

    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public WarpingRecipeCategory(IGuiHelper helper) {
        title = new TranslatableComponent(ArchitectsPalette.MOD_ID + ".info.warping_recipe_title");
        background = helper.createDrawable(new ResourceLocation(ArchitectsPalette.MOD_ID, "textures/gui/warping_recipe.png"), 0, 0, 117, 57);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(APBlocks.WARPSTONE.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends WarpingRecipe> getRecipeClass() {
        return WarpingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(WarpingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WarpingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        //Set up input item
        itemStacks.init(0, true, 15, 21);
        itemStacks.set(0, List.of(recipe.getInput().getItems()));

        //Output item
        itemStacks.init(1, false, 86, 21);
        itemStacks.set(1, recipe.getResultItem());
    }

    @Override
    public List<Component> getTooltipStrings(WarpingRecipe recipe, double mouseX, double mouseY) {
        List<Component> strings = new java.util.ArrayList<>();
        if (pointInBox(mouseX, mouseY, 49, 12, 18, 35)) {
            ResourceLocation targetDimension = recipe.getDimension();
            TranslatableComponent dimensionName = new TranslatableComponent(ArchitectsPalette.MOD_ID + ".info.dimension." + targetDimension.toString().replace(":", "."));
            TranslatableComponent tossPrompt = new TranslatableComponent(ArchitectsPalette.MOD_ID + ".info.warping_toss_description", dimensionName);

            strings.add(tossPrompt);

        }

        return strings;
    }

    private static boolean pointInBox(double x, double y, double left, double top, double width, double height) {
        return (x >= left && x <= left + width && y >= top && y <= top + height);
    }

}
