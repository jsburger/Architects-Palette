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

public class ChangeDimensionHandler {
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
}

