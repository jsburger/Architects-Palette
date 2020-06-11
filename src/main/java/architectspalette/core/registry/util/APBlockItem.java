package architectspalette.core.registry.util;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

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
    public int getBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }
}
