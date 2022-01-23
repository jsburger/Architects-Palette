package architectspalette.core.registry.util;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

public class APBlockItem extends BlockItem {
    private Integer burnTime = -1;

    public APBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    public APBlockItem setBurnTime(int ticks) {
        this.burnTime = ticks;
        return this;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, RecipeType type) {
        return this.burnTime;
    }
}
