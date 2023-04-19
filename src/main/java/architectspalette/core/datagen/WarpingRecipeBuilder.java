package architectspalette.core.datagen;

import architectspalette.core.crafting.WarpingRecipe;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class WarpingRecipeBuilder implements RecipeBuilder {

    private final ResourceKey<DimensionType> dimension;
    private final Item result;
    private final List<Ingredient> ingredients = Lists.newArrayList();;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public WarpingRecipeBuilder(Item result, ResourceKey<DimensionType> dimension, Ingredient... ingredients) {
        this.dimension = dimension;
        this.result = result;
        this.ingredients.addAll(Arrays.asList(ingredients));
    }


    @Override
    public RecipeBuilder unlockedBy(String p_176496_, CriterionTriggerInstance p_176497_) {
        this.advancement.addCriterion(p_176496_, p_176497_);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation name) {
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(name)).rewards(AdvancementRewards.Builder.recipe(name)).requirements(RequirementsStrategy.OR);
        consumer.accept(new Result(name, this.result, this.ingredients, this.dimension, this.advancement, new ResourceLocation(name.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + name.getPath())));

    }


    public static class Result implements FinishedRecipe {


        private final ResourceLocation name;
        private final Item result;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementName;
        private final ResourceKey<DimensionType> dimension;

        public Result(ResourceLocation name, Item result, List<Ingredient> ingredients, ResourceKey<DimensionType> dimension, Advancement.Builder advancement, ResourceLocation advancementName) {
            this.name = name;
            this.result = result;
            this.ingredients = ingredients;
            this.advancement = advancement;
            this.advancementName = advancementName;
            this.dimension = dimension;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray jsonarray = new JsonArray();

            for(Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }
            json.add("ingredient", jsonarray);

            JsonObject item = new JsonObject();
            item.addProperty("item", Registry.ITEM.getKey(result).toString());
            json.add("result", item);
            json.addProperty("dimension", dimension.location().toString());
        }

        @Override
        public ResourceLocation getId() {
            return name;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return WarpingRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementName;
        }
    }
}
