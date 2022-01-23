package architectspalette.common.event;

import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.registry.APSounds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Optional;

public class ChangeDimensionHandler {

    //OLD, see mixin/ItemEntityMixin for info
    public static void onDimensionsChanged (ItemEntity itemIn, ServerLevel server) {
        ResourceKey<Level> dimension = server.dimension();
        ResourceLocation dimensionId = dimension.location();
        ItemStack item = itemIn.getItem();
        for (Recipe<?> recipe : itemIn.level.getRecipeManager().getAllRecipesFor(WarpingRecipe.TYPE)) {
            if (recipe instanceof WarpingRecipe) {
                WarpingRecipe warpingRecipe = (WarpingRecipe) recipe;
                 //compareTo returns 0 if they're the same
                boolean dimensionPassed = dimensionId.compareTo(warpingRecipe.getDimension()) == 0;
                dimensionPassed = dimensionPassed || (itemIn.level.dimension().location().compareTo(warpingRecipe.getDimension()) == 0);
                if (dimensionPassed && (warpingRecipe.getInput().test(item))) {
                    itemIn.level.playSound(null, itemIn.getX(), itemIn.getY(), itemIn.getZ(), APSounds.ITEM_WARPS.get(), SoundSource.BLOCKS, 1F, 1F);
                    int n = item.getCount();
                    ItemStack out = warpingRecipe.getResultItem().copy();
                    out.setCount(n);
                    itemIn.setItem(out);
                    break;
                }
            }
        }
    }

    //New
    public static class TransformationInv extends RecipeWrapper {
        public TransformationInv() {
            super(new ItemStackHandler(1));
        }
    }

    public static TransformationInv transformationInv = new TransformationInv();

    private static Optional<WarpingRecipe> getRecipe(ItemStack item, Level world) {
        transformationInv.setItem(0, item);
        return WarpingRecipe.TYPE.find(transformationInv, world);
    }

    public static ItemStack getTransformedItem(ItemStack itemIn, Level world) {
        Optional<WarpingRecipe> recipe = getRecipe(itemIn, world);
//        if (recipe.isPresent()) {
//            return recipe.get().getCraftingResult(transformationInv);
//        }
//        return null;
        //intellij decided it could replace the above with this, kinda nuts
        return recipe.map(warpingRecipe -> warpingRecipe.assemble(transformationInv)).orElse(null);
    }


    public static void warpItem(ItemEntity itemIn, ServerLevel worldIn) {
        ItemStack baseStack = itemIn.getItem();
        ItemStack recipeStack = new ItemStack(baseStack.getItem(), 1);
        ItemStack resultStack = getTransformedItem(recipeStack, worldIn);
        if (resultStack != null) {
            resultStack.setCount(baseStack.getCount());
            itemIn.setItem(resultStack);
            itemIn.level.playSound(null, itemIn.getX(), itemIn.getY(), itemIn.getZ(), APSounds.ITEM_WARPS.get(), SoundSource.BLOCKS, 1F, 1F);
        }
    }



}

