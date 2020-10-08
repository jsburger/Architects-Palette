package architectspalette.common.event;

import architectspalette.core.crafting.WarpingRecipe;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ChangeDimensionHandler {
    public static void onDimensionsChanged (ItemEntity itemIn, ServerWorld server){
//        LOGGER.error("DIMENSION CHANGED!");
        RegistryKey<World> dimension = server.getDimensionKey();
        ResourceLocation dimensionId = dimension.getLocation();
//        LOGGER.debug("Server dimension in: " + dimensionId.toString());
        if (itemIn.getType() == EntityType.ITEM) {
            ItemStack item = itemIn.getItem();
//            LOGGER.debug("Type check passed, Item Stack is " + item.toString());

            for (IRecipe<?> recipe : itemIn.world.getRecipeManager().getRecipesForType(WarpingRecipe.TYPE)) {
                if (recipe instanceof WarpingRecipe) {
                    WarpingRecipe warpingRecipe = (WarpingRecipe) recipe;
//                    LOGGER.debug("Dimension comparison:");
//                    LOGGER.debug(dimensionId.compareTo(warpingRecipe.getDimension()));
//                    LOGGER.debug("Input Test;");
//                    LOGGER.debug(warpingRecipe.getInput().test(item));
                     //compareTo returns 0 if they're the same
                    boolean dimensionPassed = dimensionId.compareTo(warpingRecipe.getDimension()) == 0;
                    dimensionPassed = dimensionPassed || (itemIn.world.getDimensionKey().getLocation().compareTo(warpingRecipe.getDimension()) == 0);
                    if (dimensionPassed && (warpingRecipe.getInput().test(item))) {
//                        LOGGER.debug("ALL TESTS PASSED");
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
}

