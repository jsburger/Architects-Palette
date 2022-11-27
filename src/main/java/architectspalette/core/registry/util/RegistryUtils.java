package architectspalette.core.registry.util;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RegistryUtils {
	
	public static <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {
		return APItems.ITEMS.register(name, supplier);
	}

	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier) {
		return createBlock(name, supplier, CreativeModeTab.TAB_BUILDING_BLOCKS);
	}

	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, @Nullable CreativeModeTab group) {
		RegistryObject<B> block = APBlocks.BLOCKS.register(name, supplier);
		APItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(group)));
		return block;
	}
	
	public static <B extends Block> RegistryObject<B> createBlockNoItem(String name, Supplier<? extends B> supplier) {
		return APBlocks.BLOCKS.register(name, supplier);
	}

//	public static <B extends Block> StoneBlockSet createBoardSet(String name, Supplier<? extends B> supplier) {
//		StoneBlockSet boardSet = new StoneBlockSet(createBlock(name, supplier), StoneBlockSet.SetGroup.NO_WALLS);
//		boardSet.forEachRegistryObject((obj) -> {
//			ModelBakeEventHandler.register(obj, BoardModel::new);
//		});
//		return boardSet;
//	}
}