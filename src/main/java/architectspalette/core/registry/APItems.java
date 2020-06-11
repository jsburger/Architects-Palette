package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.APBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class APItems {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ArchitectsPalette.MOD_ID);

	// Todo: Find a better solution for this
	public static final RegistryObject<Item> CHARCOAL_BLOCK = ITEMS.register("charcoal_block", () -> new APBlockItem(APBlocks.CHARCOAL_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setBurnTime(1600));

}