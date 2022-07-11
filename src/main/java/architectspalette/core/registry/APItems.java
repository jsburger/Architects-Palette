package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.APBlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    public static final RegistryObject<Item> UNOBTANIUM = ITEMS.register("unobtanium", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> BRASS_BLEND = ITEMS.register("nether_brass_blend", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> NETHER_BRASS = ITEMS.register("nether_brass_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> NETHER_BRASS_NUGGET = ITEMS.register("nether_brass_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> NETHER_BRASS_TORCH = ITEMS.register("nether_brass_torch", () -> new StandingAndWallBlockItem(APBlocks.NETHER_BRASS_TORCH.get(), APBlocks.NETHER_BRASS_WALL_TORCH.get(),new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> WARDSTONE_BLEND = ITEMS.register("wardstone_blend", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> WARDSTONE_BRICK = ITEMS.register("wardstone_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
}