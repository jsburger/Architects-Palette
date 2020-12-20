package architectspalette.core.crafting;

import architectspalette.core.ArchitectsPalette;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class WarpingRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();
    public static WarpingRecipe.RecipeType TYPE = new RecipeType();

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
    public boolean matches(IInventory inv, World worldIn) {
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.getRecipeOutput().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return WarpingRecipe.TYPE;
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<WarpingRecipe> {

        Serializer() {
            this.setRegistryName(new ResourceLocation(ArchitectsPalette.MOD_ID, "warping"));
        }

        @Override
        public WarpingRecipe read(ResourceLocation recipeId, JsonObject json) {

            final JsonElement inputElement = JSONUtils.isJsonArray(json, "ingredient") ?
                    JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
            final Ingredient input = Ingredient.deserialize(inputElement);
            final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            final ResourceLocation dimensionId = new ResourceLocation(JSONUtils.getString(json, "dimension"));

            return new WarpingRecipe(recipeId, input, output, dimensionId);
        }

        @Nullable
        @Override
        public WarpingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

            final Ingredient input = Ingredient.read(buffer);
            final ItemStack output = buffer.readItemStack();
            final ResourceLocation dimensionId = buffer.readResourceLocation();

            return new WarpingRecipe(recipeId, input, output, dimensionId);
        }

        @Override
        public void write(PacketBuffer buffer, WarpingRecipe recipe) {
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output);
            buffer.writeResourceLocation(recipe.dimension);
        }
    }

    public static class RecipeType implements IRecipeType<WarpingRecipe> {

        @Override
        public String toString() {
            return ArchitectsPalette.MOD_ID.concat(":warping");
        }
    }
}
