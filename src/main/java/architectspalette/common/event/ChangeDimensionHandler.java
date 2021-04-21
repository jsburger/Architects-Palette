package architectspalette.common.event;

import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.registry.APSounds;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Optional;

public class ChangeDimensionHandler {

    //OLD, see mixin/ItemEntityMixin for info
    public static void onDimensionsChanged (ItemEntity itemIn, ServerWorld server) {
        RegistryKey<World> dimension = server.getDimensionKey();
        ResourceLocation dimensionId = dimension.getLocation();
        ItemStack item = itemIn.getItem();
        for (IRecipe<?> recipe : itemIn.world.getRecipeManager().getRecipesForType(WarpingRecipe.TYPE)) {
            if (recipe instanceof WarpingRecipe) {
                WarpingRecipe warpingRecipe = (WarpingRecipe) recipe;
                 //compareTo returns 0 if they're the same
                boolean dimensionPassed = dimensionId.compareTo(warpingRecipe.getDimension()) == 0;
                dimensionPassed = dimensionPassed || (itemIn.world.getDimensionKey().getLocation().compareTo(warpingRecipe.getDimension()) == 0);
                if (dimensionPassed && (warpingRecipe.getInput().test(item))) {
                    itemIn.world.playSound(null, itemIn.getPosX(), itemIn.getPosY(), itemIn.getPosZ(), APSounds.ITEM_WARPS.get(), SoundCategory.BLOCKS, 1F, 1F);
                    int n = item.getCount();
                    ItemStack out = warpingRecipe.getRecipeOutput().copy();
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

    private static Optional<WarpingRecipe> getRecipe(ItemStack item, World world) {
        transformationInv.setInventorySlotContents(0, item);
        return WarpingRecipe.TYPE.find(transformationInv, world);
    }

    public static ItemStack getTransformedItem(ItemStack itemIn, World world) {
        Optional<WarpingRecipe> recipe = getRecipe(itemIn, world);
//        if (recipe.isPresent()) {
//            return recipe.get().getCraftingResult(transformationInv);
//        }
//        return null;
        //intellij decided it could replace the above with this, kinda nuts
        return recipe.map(warpingRecipe -> warpingRecipe.getCraftingResult(transformationInv)).orElse(null);
    }


    public static void warpItem(ItemEntity itemIn, ServerWorld worldIn) {
        ItemStack baseStack = itemIn.getItem();
        ItemStack recipeStack = new ItemStack(baseStack.getItem(), 1);
        ItemStack resultStack = getTransformedItem(recipeStack, worldIn);
        if (resultStack != null) {
            resultStack.setCount(baseStack.getCount());
            itemIn.setItem(resultStack);
            itemIn.world.playSound(null, itemIn.getPosX(), itemIn.getPosY(), itemIn.getPosZ(), APSounds.ITEM_WARPS.get(), SoundCategory.BLOCKS, 1F, 1F);
        }
    }



}

