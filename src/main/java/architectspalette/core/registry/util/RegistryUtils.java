package architectspalette.core.registry.util;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class RegistryUtils {
	
	public static <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {
		RegistryObject<I> item = APItems.ITEMS.register(name, supplier);
		return item;
	}
	
	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
		RegistryObject<B> block = APBlocks.BLOCKS.register(name, supplier);
		APItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(group)));
		return block;
	}
	
	public static <B extends Block> RegistryObject<B> createBlockNoItem(String name, Supplier<? extends B> supplier) {
		RegistryObject<B> block = APBlocks.BLOCKS.register(name, supplier);
		return block;
	}
	
}