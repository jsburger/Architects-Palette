package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.APBlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class APItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArchitectsPalette.MOD_ID);

    // Todo: Find a better solution for this
    public static final RegistryObject<Item> CHARCOAL_BLOCK = ITEMS.register("charcoal_block", () -> new APBlockItem(APBlocks.CHARCOAL_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)).setBurnTime(1600));

    public static final RegistryObject<Item> ALGAL_BLEND = ITEMS.register("algal_blend", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> ALGAL_BRICK = ITEMS.register("algal_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> WITHERED_BONE = ITEMS.register("withered_bone", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> ENTWINE_ROD = ITEMS.register("entwine_rod", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> SUNMETAL_BLEND = ITEMS.register("sunmetal_blend", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> SUNMETAL_BRICK = ITEMS.register("sunmetal_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

}