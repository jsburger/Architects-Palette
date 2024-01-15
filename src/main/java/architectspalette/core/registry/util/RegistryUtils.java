package architectspalette.core.registry.util;

import architectspalette.core.event.CreativeModeTabEventHandler;
import architectspalette.core.event.ModelBakeEventHandler;
import architectspalette.core.model.BoardModel;
import architectspalette.core.model.util.SpriteShift;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class RegistryUtils {



	public static RegistryObject<Item> createItem(String name) {
		return createItem(name, APItems::resourceItem);
	}
	public static <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {
		return createItem(name, supplier, CreativeModeTabs.INGREDIENTS);
	}
	public static <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier, ResourceKey<CreativeModeTab> group) {
		RegistryObject<I> item = APItems.ITEMS.register(name, supplier);
		CreativeModeTabEventHandler.assignItemToTab(item, group);
		return item;
	}

	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier) {
		return createBlock(name, supplier, CreativeModeTabs.BUILDING_BLOCKS);
	}

	@SafeVarargs
	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, ResourceKey<CreativeModeTab>... group) {
		RegistryObject<B> block = APBlocks.BLOCKS.register(name, supplier);
		APItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		if (group != null) CreativeModeTabEventHandler.assignItemToTab(block, group);
		return block;
	}
	
	public static <B extends Block> RegistryObject<B> createBlockNoItem(String name, Supplier<? extends B> supplier) {
		return APBlocks.BLOCKS.register(name, supplier);
	}


	public static <B extends Block> StoneBlockSet createBoardSet(String name, Supplier<? extends B> supplier) {
		StoneBlockSet boardSet = new StoneBlockSet(createBlock(name, supplier), StoneBlockSet.SetGroup.NO_WALLS).woodify();
		boardSet.forEachRegistryObject((obj) -> ModelBakeEventHandler.register(obj, model -> new BoardModel(model, SpriteShift.getShift("block/" + name, "block/" + name + "_odd"))));
		return boardSet;
	}
}