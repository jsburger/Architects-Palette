package architectspalette.core.crafting;

import architectspalette.core.ArchitectsPalette;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;

public class WarpingRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();
    public static WarpingRecipe.WarpRecipeType TYPE = new WarpRecipeType();

    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation dimension;
    private final ResourceLocation id;

    public WarpingRecipe(ResourceLocation id, Ingredient input, ItemStack output, ResourceLocation dimension) {
        this.input = input;
        this.output = output;
        this.dimension = dimension;
        this.id = id;
    }

    public ResourceLocation getDimension() {
        return this.dimension;
    }

    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return this.input.test(inv.getItem(0)) && (this.dimension.compareTo(worldIn.dimension().location()) == 0);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(Container inv) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(this.input);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return WarpingRecipe.TYPE;
    }

    public static class Serializer implements RecipeSerializer<WarpingRecipe> {
        @Override
        public WarpingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            final JsonElement inputElement = GsonHelper.isArrayNode(json, "ingredient") ?
                    GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            final Ingredient input = Ingredient.fromJson(inputElement);
            final ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            final ResourceLocation dimensionId = new ResourceLocation(GsonHelper.getAsString(json, "dimension"));

            return new WarpingRecipe(recipeId, input, output, dimensionId);
        }

        @Nullable
        @Override
        public WarpingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            final Ingredient input = Ingredient.fromNetwork(buffer);
            final ItemStack output = buffer.readItem();
            final ResourceLocation dimensionId = buffer.readResourceLocation();

            return new WarpingRecipe(recipeId, input, output, dimensionId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WarpingRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeResourceLocation(recipe.dimension);
        }
    }

    public static class WarpRecipeType implements RecipeType<WarpingRecipe> {

        @Override
        public String toString() {
            return "warping";
        }

        public <C extends Container> Optional<WarpingRecipe> find(C inv, Level world) {
            return world.getRecipeManager()
                    .getRecipeFor(WarpingRecipe.TYPE, inv, world);
        }

    }
}
