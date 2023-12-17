package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.APBlockItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static architectspalette.core.registry.util.RegistryUtils.createItem;

public class APItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArchitectsPalette.MOD_ID);
    public static Item resourceItem() { return new Item(new Item.Properties()); }

    public static final RegistryObject<Item> CHARCOAL_BLOCK = createItem("charcoal_block", () -> new APBlockItem(APBlocks.CHARCOAL_BLOCK.get(), new Item.Properties()).setBurnTime(1600), CreativeModeTabs.BUILDING_BLOCKS);

    public static final RegistryObject<Item> ALGAL_BLEND = createItem("algal_blend");
    public static final RegistryObject<Item> ALGAL_BRICK = createItem("algal_brick");

    public static final RegistryObject<Item> WITHERED_BONE = createItem("withered_bone");
    public static final RegistryObject<Item> ENTWINE_ROD = createItem("entwine_rod");
    public static final RegistryObject<Item> SUNMETAL_BLEND = createItem("sunmetal_blend");
    public static final RegistryObject<Item> SUNMETAL_BRICK = createItem("sunmetal_brick");

    public static final RegistryObject<Item> UNOBTANIUM = createItem("unobtanium");

    public static final RegistryObject<Item> BRASS_BLEND = createItem("nether_brass_blend");
    public static final RegistryObject<Item> NETHER_BRASS = createItem("nether_brass_ingot");
    public static final RegistryObject<Item> NETHER_BRASS_NUGGET = createItem("nether_brass_nugget");
    public static final RegistryObject<Item> NETHER_BRASS_TORCH = createItem("nether_brass_torch", () -> new StandingAndWallBlockItem(APBlocks.NETHER_BRASS_TORCH.get(), APBlocks.NETHER_BRASS_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    public static final RegistryObject<Item> WARDSTONE_BLEND = createItem("wardstone_blend");
    public static final RegistryObject<Item> WARDSTONE_BRICK = createItem("wardstone_brick");

    public static final RegistryObject<Item> ORACLE_JELLY = createItem("oracle_jelly");
    public static final RegistryObject<Item> CEREBRAL_PLATE = createItem("cerebral_plate");
}