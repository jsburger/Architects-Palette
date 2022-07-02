package architectspalette.core.registry;

import architectspalette.content.blocks.PipeBlock;
import architectspalette.content.blocks.*;
import architectspalette.content.blocks.abyssaline.*;
import architectspalette.content.blocks.entrails.DrippyBlock;
import architectspalette.content.blocks.entrails.DrippySlabBlock;
import architectspalette.content.blocks.entrails.DrippyVerticalSlabBlock;
import architectspalette.content.blocks.flint.FlintBlock;
import architectspalette.content.blocks.flint.FlintPillarBlock;
import architectspalette.content.worldgen.features.TwistedTree;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static architectspalette.core.registry.util.RegistryUtils.createBlock;
import static architectspalette.core.registry.util.RegistryUtils.createBlockNoItem;
import static architectspalette.core.registry.util.StoneBlockSet.SetGroup.NO_STAIRS;
import static architectspalette.core.registry.util.StoneBlockSet.SetGroup.NO_WALLS;

public class APBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArchitectsPalette.MOD_ID);

    // Abyssaline
    public static final RegistryObject<AbyssalineBlock>      ABYSSALINE                 = createBlock("abyssaline",                 () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE));
    public static final RegistryObject<AbyssalineBlock>      ABYSSALINE_BRICKS          = createBlock("abyssaline_bricks",          () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE));
    public static final RegistryObject<ChiseledAbyssalineBlock> CHISELED_ABYSSALINE_BRICKS = createBlock("chiseled_abyssaline_bricks", () -> new ChiseledAbyssalineBlock(APBlockProperties.CHISELED_ABYSSALINE));
    public static final RegistryObject<AbyssalinePillarBlock>   ABYSSALINE_PILLAR          = createBlock("abyssaline_pillar",          () -> new AbyssalinePillarBlock(APBlockProperties.ABYSSALINE));
    public static final RegistryObject<AbyssalineSlabBlock>     ABYSSALINE_BRICK_SLAB      = createBlock("abyssaline_brick_slab",      () -> new AbyssalineSlabBlock(APBlockProperties.ABYSSALINE));
    public static final RegistryObject<AbyssalineBlock>      ABYSSALINE_TILES           = createBlock("abyssaline_tiles",           () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE));
    public static final RegistryObject<AbyssalineSlabBlock>     ABYSSALINE_TILE_SLAB       = createBlock("abyssaline_tile_slab",       () -> new AbyssalineSlabBlock(APBlockProperties.ABYSSALINE));
    public static final RegistryObject<AbyssalineLampBlock>     ABYSSALINE_LAMP_BLOCK      = createBlock("abyssaline_lamp",            () -> new AbyssalineLampBlock(APBlockProperties.ABYSSALINE_LAMP.sound(SoundType.GLASS)));
//    public static final RegistryObject<AbyssalineBrickStairsBlock> ABYSSALINE_BRICK_STAIRS    = createBlock("abyssaline_brick_stairs",    () -> new AbyssalineBrickStairsBlock(() -> ABYSSALINE_BRICKS.get().getDefaultState(), APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
//    public static final RegistryObject<AbyssalineBrickWallBlock>   ABYSSALINE_BRICK_WALL      = createBlock("abyssaline_brick_wall",      () -> new AbyssalineBrickWallBlock(APBlockProperties.ABYSSALINE), ItemGroup.DECORATIONS);
//    public static final RegistryObject<AbyssalineBrickStairsBlock> ABYSSALINE_TILE_STAIRS    = createBlock("abyssaline_tile_stairs",    () -> new AbyssalineBrickStairsBlock(() -> ABYSSALINE_TILES.get().getDefaultState(), APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);

    public static final RegistryObject<AbyssalineVerticalSlabBlock>     ABYSSALINE_BRICK_VERTICAL_SLAB      = createBlock("abyssaline_brick_vertical_slab",      () -> new AbyssalineVerticalSlabBlock(APBlockProperties.ABYSSALINE));
    public static final RegistryObject<AbyssalineVerticalSlabBlock>     ABYSSALINE_TILE_VERTICAL_SLAB       = createBlock("abyssaline_tile_vertical_slab",       () -> new AbyssalineVerticalSlabBlock(APBlockProperties.ABYSSALINE));
    
    // Villager Trade blocks
     // Funny fish blocks
    public static final RegistryObject<Block>    SALMON_LOG = createBlock("salmon_log",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.TERRACOTTA_RED)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block>       COD_LOG = createBlock("cod_log",       () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.TERRACOTTA_YELLOW)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SALMON_SCALES = createBlock("salmon_scales", () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.TERRACOTTA_RED)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block>    COD_SCALES = createBlock("cod_scales",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.TERRACOTTA_YELLOW)), CreativeModeTab.TAB_BUILDING_BLOCKS);
     // Entrails
    public static final RegistryObject<Block> ENTRAILS               = createBlock("entrails", () -> new DrippyBlock(APBlockProperties.Meat(MaterialColor.TERRACOTTA_PINK)));
    public static final RegistryObject<Block> ENTRAILS_SLAB          = createBlock("entrails_slab", () -> new DrippySlabBlock(APBlockProperties.Meat(MaterialColor.TERRACOTTA_PINK)));
    public static final RegistryObject<Block> ENTRAILS_VERTICAL_SLAB = createBlock("entrails_vertical_slab", () -> new DrippyVerticalSlabBlock(APBlockProperties.Meat(MaterialColor.TERRACOTTA_PINK)));
    public static final RegistryObject<Block> ENTRAILS_STAIRS =        createBlock("entrails_stairs", () -> new StairBlock(() -> ENTRAILS.get().defaultBlockState(), APBlockProperties.Meat(MaterialColor.TERRACOTTA_PINK)));
     // Plating & Piping
    public static final StoneBlockSet PLATING_BLOCK = new StoneBlockSet(createBlock("plating_block", () -> new Block(APBlockProperties.PLATING)));
    public static final RegistryObject<Block> PIPE = createBlock("pipe", () -> new PipeBlock(APBlockProperties.PLATING.noOcclusion()));
     //Spools
    public static final RegistryObject<Block> SPOOL = createBlock("spool", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));

    // Charcoal Block
    public static final RegistryObject<Block> CHARCOAL_BLOCK = createBlockNoItem("charcoal_block", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.COAL_BLOCK)));

    // Myonite (previously limestone)
    public static final StoneBlockSet MYONITE             = new StoneBlockSet(createBlock("myonite",              () -> new Block(APBlockProperties.MYONITE)));
    public static final StoneBlockSet MYONITE_BRICK       = new StoneBlockSet(createBlock("myonite_bricks",       () -> new Block(APBlockProperties.MYONITE)));
    public static final StoneBlockSet MUSHY_MYONITE_BRICK = new StoneBlockSet(createBlock("mushy_myonite_bricks", () -> new Block(APBlockProperties.MYONITE)));

    // Olivestone
    public static final StoneBlockSet OLIVESTONE_BRICK = new StoneBlockSet(createBlock("olivestone_bricks", () -> new Block(APBlockProperties.OLIVESTONE)));
    public static final StoneBlockSet OLIVESTONE_TILE  = new StoneBlockSet(createBlock("olivestone_tiles",  () -> new Block(APBlockProperties.OLIVESTONE)));

    public static final RegistryObject<Block> OLIVESTONE_PILLAR         = createBlock("olivestone_pillar", () -> new RotatedPillarBlock(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> CRACKED_OLIVESTONE_BRICKS = createBlock("cracked_olivestone_bricks", () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> CRACKED_OLIVESTONE_TILES  = createBlock("cracked_olivestone_tiles",  () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> CHISELED_OLIVESTONE       = createBlock("chiseled_olivestone", () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> ILLUMINATED_OLIVESTONE    = createBlock("illuminated_olivestone", () -> new Block(BlockBehaviour.Properties.copy(OLIVESTONE_BRICK.get()).lightLevel((state) -> 15)));

    // Algal Brick
    public static final StoneBlockSet ALGAL_BRICK = new StoneBlockSet(createBlock("algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));
    public static final RegistryObject<Block> CRACKED_ALGAL_BRICKS  = createBlock("cracked_algal_bricks",  () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final RegistryObject<Block> CHISELED_ALGAL_BRICKS = createBlock("chiseled_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final StoneBlockSet OVERGROWN_ALGAL_BRICK = new StoneBlockSet(createBlock("overgrown_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));
    public static final RegistryObject<Block> ALGAL_LAMP = createBlock("algal_lamp", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN)));

    // Ore Bricks
    public static final List<StoneBlockSet> ORE_BRICKS = addOreBricks();

    private static List<StoneBlockSet> addOreBricks() {
        List<String> ores = Arrays.asList("coal", "lapis", "redstone", "iron", "gold", "emerald", "diamond");
        List<StoneBlockSet> l = new LinkedList<>();
        ores.forEach((ore) -> {
                StoneBlockSet set = new StoneBlockSet(createBlock(ore + "_ore_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE_BRICKS))));
                createBlock("cracked_" + ore + "_ore_bricks", () -> new Block(Block.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
                createBlock("chiseled_" + ore + "_ore_bricks", () -> new Block(Block.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
                l.add(set);
            }
        );
        return l;
    }

    // Flint Blocks
    public static final RegistryObject<Block> FLINT_BLOCK  	= createBlock("flint_block",  () -> new FlintBlock(APBlockProperties.FLINT));
    public static final StoneBlockSet FLINT_TILES  			= new StoneBlockSet(createBlock("flint_tiles",  () -> new FlintBlock(APBlockProperties.FLINT)));
    public static final RegistryObject<Block> FLINT_PILLAR 	= createBlock("flint_pillar", () -> new FlintPillarBlock(APBlockProperties.FLINT));

    // Polished Packed Ice
    public static final StoneBlockSet POLISHED_PACKED_ICE = new StoneBlockSet(createBlock("polished_packed_ice", () -> new Block(APBlockProperties.BUILDING_ICE)));
    public static final RegistryObject<Block> CHISELED_PACKED_ICE = createBlock("chiseled_packed_ice", () -> new Block(APBlockProperties.BUILDING_ICE));
    public static final RegistryObject<Block> PACKED_ICE_PILLAR   = createBlock("packed_ice_pillar",   () -> new RotatedPillarBlock(APBlockProperties.BUILDING_ICE));

    // Sunmetal
    public static final StoneBlockSet SUNMETAL = new StoneBlockSet(createBlock("sunmetal_block", () -> new Block(APBlockProperties.SUNMETAL)), NO_WALLS);
    public static final RegistryObject<Block> CHISELED_SUNMETAL_BLOCK = createBlock("chiseled_sunmetal_block", () -> new Block(APBlockProperties.SUNMETAL));
    public static final RegistryObject<Block> SUNMETAL_PILLAR         = createBlock("sunmetal_pillar", () -> new RotatedPillarBlock(APBlockProperties.SUNMETAL));
    public static final RegistryObject<Block> SUNMETAL_BARS           = createBlock("sunmetal_bars", () -> new IronBarsBlock(APBlockProperties.SUNMETAL.noOcclusion()));

    // Osseous Bricks
    public static final StoneBlockSet OSSEOUS_BRICK = new StoneBlockSet(createBlock("osseous_bricks", () -> new Block(Block.Properties.copy(Blocks.BONE_BLOCK))));
    public static final RegistryObject<Block> OSSEOUS_PILLAR = createBlock("osseous_pillar", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> OSSEOUS_SKULL = createBlock("osseous_skull", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> LIT_OSSEOUS_SKULL = createBlock("lit_osseous_skull", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel(e -> 12)));
    // Withered
     // Todo: Replace bone block recipe to one that uses withered bone meal if that gets in
    public static final RegistryObject<Block> WITHERED_BONE_BLOCK = createBlock("withered_bone_block", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.BONE_BLOCK)));
    public static final StoneBlockSet      WITHERED_OSSEOUS_BRICK = new StoneBlockSet(createBlock("withered_osseous_bricks", () -> new Block(Block.Properties.copy(Blocks.BONE_BLOCK))));
    public static final RegistryObject<Block> WITHERED_OSSEOUS_PILLAR = createBlock("withered_osseous_pillar", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> WITHERED_OSSEOUS_SKULL = createBlock("withered_osseous_skull", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> LIT_WITHERED_OSSEOUS_SKULL = createBlock("lit_withered_osseous_skull", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel(e -> 12)));
    // Wither Lamp
    public static final RegistryObject<Block> WITHER_LAMP = createBlock("wither_lamp", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN)));

    // Entwine
    public static final StoneBlockSet ENTWINE = new StoneBlockSet(createBlock("entwine_block", () -> new Block(APBlockProperties.ENTWINE)), NO_WALLS);
    public static final RegistryObject<Block> ENTWINE_PILLAR = createBlock("entwine_pillar", () -> new RotatedPillarBlock(APBlockProperties.ENTWINE));
    public static final RegistryObject<Block> CHISELED_ENTWINE = createBlock("chiseled_entwine", () -> new Block(APBlockProperties.ENTWINE));
    public static final RegistryObject<Block> ENTWINE_BARS = createBlock("entwine_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.copy(ENTWINE.get()).noOcclusion()));

    // Heavy Stone Bricks
    public static final RegistryObject<Block> HEAVY_STONE_BRICKS = createBlock("heavy_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> HEAVY_MOSSY_STONE_BRICKS = createBlock("heavy_mossy_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS)));
    public static final RegistryObject<Block> HEAVY_CRACKED_STONE_BRICKS = createBlock("heavy_cracked_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));

    // Polished Glowstone
    public static final StoneBlockSet POLISHED_GLOWSTONE = new StoneBlockSet(createBlock("polished_glowstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE))), NO_STAIRS);
    public static final RegistryObject<Block> RUNIC_GLOWSTONE = createBlock("runic_glowstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE)));

    // Scute Block
    public static final RegistryObject<Block> SCUTE_BLOCK = createBlock("scute_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(5.0F, 6.0F).sound(SoundType.BASALT)));
    // Rotten Flesh Block
    public static final RegistryObject<Block> ROTTEN_FLESH_BLOCK = createBlock("rotten_flesh_block", () -> new Block(APBlockProperties.Meat(MaterialColor.COLOR_ORANGE)));

    // Gilded Sandstone
    public static final StoneBlockSet GILDED_SANDSTONE = new StoneBlockSet(createBlock("gilded_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE))), NO_WALLS);
    public static final RegistryObject<Block> GILDED_SANDSTONE_PILLAR = createBlock("gilded_sandstone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> CHISELED_GILDED_SANDSTONE = createBlock("chiseled_gilded_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));

    // Mossy Blackstone Variants
    public static final RegistryObject<Block> WEEPING_BLACKSTONE = createBlock("weeping_blackstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE)));
    public static final RegistryObject<Block> TWISTING_BLACKSTONE = createBlock("twisting_blackstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE)));
    public static final RegistryObject<Block> WEEPING_BLACKSTONE_BRICKS = createBlock("weeping_blackstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));
    public static final RegistryObject<Block> TWISTING_BLACKSTONE_BRICKS = createBlock("twisting_blackstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    // End Stone Variants
    public static final RegistryObject<Block>   CHORAL_END_STONE_BRICKS = createBlock("choral_end_stone_bricks",   () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS)));
    public static final RegistryObject<Block>  CRACKED_END_STONE_BRICKS = createBlock("cracked_end_stone_bricks",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS)));
    public static final RegistryObject<Block> CHISELED_END_STONE_BRICKS = createBlock("chiseled_end_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS)));

    // Warpstone
    public static final StoneBlockSet WARPSTONE = new StoneBlockSet(createBlock("warpstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE))));

    // Twisted Wood
     // Todo: Bookshelf, sign(?), boat(?)
    public static final StoneBlockSet TWISTED_PLANKS = new StoneBlockSet(createBlock("twisted_planks", () -> new Block(APBlockProperties.TwistedWood())), NO_WALLS);

    public static final RegistryObject<Block>  STRIPPED_TWISTED_LOG = createBlock("stripped_twisted_log", () -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final RegistryObject<Block> STRIPPED_TWISTED_WOOD = createBlock("stripped_twisted_wood",() -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final RegistryObject<Block>           TWISTED_LOG = createBlock("twisted_log",          () -> new StrippableLogBlock(APBlockProperties.TwistedWood(), STRIPPED_TWISTED_LOG.get()));
    public static final RegistryObject<Block>          TWISTED_WOOD = createBlock("twisted_wood",         () -> new StrippableLogBlock(APBlockProperties.TwistedWood(), STRIPPED_TWISTED_WOOD.get()));
    public static final RegistryObject<Block>        TWISTED_LEAVES = createBlock("twisted_leaves",       () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block>         TWISTED_FENCE = createBlock("twisted_fence",        () -> new         FenceBlock(APBlockProperties.TwistedWood()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block>    TWISTED_FENCE_GATE = createBlock("twisted_fence_gate",   () -> new     FenceGateBlock(APBlockProperties.TwistedWood()), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block>          TWISTED_DOOR = createBlock("twisted_door",         () -> new          DoorBlock(APBlockProperties.TwistedWood().noOcclusion()), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block>      TWISTED_TRAPDOOR = createBlock("twisted_trapdoor",     () -> new      TrapDoorBlock(APBlockProperties.TwistedWood().noOcclusion()), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block>        TWISTED_BUTTON = createBlock("twisted_button",       () -> new    WoodButtonBlock(APBlockProperties.TwistedWood(true)), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> TWISTED_PRESSURE_PLATE = createBlock("twisted_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, APBlockProperties.TwistedWood(true)), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block>        TWISTED_SAPLING = createBlock("twisted_sapling", () -> new SaplingBlock(new TwistedTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> POTTED_TWISTED_SAPLING = createPottedPlant(TWISTED_SAPLING);

    // Basalt Tiles
    public static final StoneBlockSet BASALT_TILES = new StoneBlockSet(createBlock("basalt_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT))));
    public static final RegistryObject<Block>  CRACKED_BASALT_TILES = createBlock("cracked_basalt_tiles",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT)));
    public static final RegistryObject<Block> CHISELED_BASALT_TILES = createBlock("chiseled_basalt_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT)));

    // Celestial Stones
    public static final RegistryObject<Block> SUNSTONE  = createBlock("sunstone",  () -> new SunstoneBlock(APBlockProperties.SUNSTONE, SunstoneBlock::sunstoneLight));
    public static final RegistryObject<Block> MOONSTONE = createBlock("moonstone", () -> new SunstoneBlock(APBlockProperties.SUNSTONE, SunstoneBlock::moonstoneLight));

    // Odd block variants
    public static final RegistryObject<Block> MOLTEN_NETHER_BRICKS = createBlock("molten_nether_bricks", () -> new Block(APBlockProperties.MOLTEN_BRICK));
    public static final RegistryObject<Block> COARSE_SNOW = createBlock("coarse_snow", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)));

    // Heavy End Stone Bricks
    public static final RegistryObject<Block> HEAVY_END_STONE_BRICKS = createBlock("heavy_end_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS), BigBrickBlock.BrickType.END_STONE));
    public static final RegistryObject<Block> HEAVY_CRACKED_END_STONE_BRICKS = createBlock("heavy_cracked_end_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS), BigBrickBlock.BrickType.END_STONE));

    // Cage Lanterns
    public static final RegistryObject<Block> REDSTONE_CAGE_LANTERN  = createBlock("redstone_cage_lantern", () -> new CageLanternBlock(APBlockProperties.CAGE_LANTERN, 3), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> GLOWSTONE_CAGE_LANTERN = createBlock("glowstone_cage_lantern", () -> new CageLanternBlock(APBlockProperties.CAGE_LANTERN, 3), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> ALGAL_CAGE_LANTERN     = createBlock("algal_cage_lantern", () -> new CageLanternBlock(APBlockProperties.CAGE_LANTERN, 3), CreativeModeTab.TAB_REDSTONE);

    // Acacia Totems
    public static final RegistryObject<TotemWingBlock> ACACIA_TOTEM_WING = createBlock("acacia_totem_wing", () -> new TotemWingBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS).noOcclusion().noDrops().sound(SoundType.SCAFFOLDING).noCollission()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GRINNING_ACACIA_TOTEM = createBlock("grinning_acacia_totem", () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.GRINNING));
    public static final RegistryObject<Block> PLACID_ACACIA_TOTEM = createBlock("placid_acacia_totem", () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.PLACID));
    public static final RegistryObject<Block> SHOCKED_ACACIA_TOTEM = createBlock("shocked_acacia_totem", () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.SHOCKED));
    public static final RegistryObject<Block> BLANK_ACACIA_TOTEM = createBlock("blank_acacia_totem", () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.BLANK));

    // Ender Pearl Block
    public static final RegistryObject<Block> ENDER_PEARL_BLOCK = createBlock("ender_pearl_block", () -> new Block(APBlockProperties.ENDER_PEARL));

    // Boards
    public static final RegistryObject<Block> OAK_BOARDS = createBlock("oak_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> BIRCH_BOARDS = createBlock("birch_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<Block> SPRUCE_BOARDS = createBlock("spruce_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<Block> JUNGLE_BOARDS = createBlock("jungle_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_PLANKS)));
    public static final RegistryObject<Block> DARK_OAK_BOARDS = createBlock("dark_oak_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryObject<Block> ACACIA_BOARDS = createBlock("acacia_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<Block> CRIMSON_BOARDS = createBlock("crimson_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistryObject<Block> WARPED_BOARDS = createBlock("warped_boards", () -> new BoardBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS)));
    public static final RegistryObject<Block> TWISTED_BOARDS = createBlock("twisted_boards", () -> new BoardBlock(APBlockProperties.TwistedWood()));

    // Railings
    public static final RegistryObject<Block> OAK_RAILING = createBlock("oak_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BIRCH_RAILING = createBlock("birch_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> SPRUCE_RAILING = createBlock("spruce_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> JUNGLE_RAILING = createBlock("jungle_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DARK_OAK_RAILING = createBlock("dark_oak_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ACACIA_RAILING = createBlock("acacia_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CRIMSON_RAILING = createBlock("crimson_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> WARPED_RAILING = createBlock("warped_railing", () -> new RailingBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> TWISTED_RAILING = createBlock("twisted_railing", () -> new RailingBlock(APBlockProperties.TwistedWood()), CreativeModeTab.TAB_DECORATIONS);

    // New stone block sets
    // Dripstone
    public static final StoneBlockSet DRIPSTONE_BRICKS = new StoneBlockSet(createBlock("dripstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DRIPSTONE_BLOCK))));
    public static final RegistryObject<Block> DRIPSTONE_PILLAR = createBlock("dripstone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DRIPSTONE_BLOCK)));
    public static final RegistryObject<Block> CHISELED_DRIPSTONE = createBlock("chiseled_dripstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DRIPSTONE_BLOCK)));
    public static final RegistryObject<Block> HEAVY_DRIPSTONE_BRICKS = createBlock("heavy_dripstone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.DRIPSTONE_BLOCK), BigBrickBlock.BrickType.DRIPSTONE));
    public static final RegistryObject<Block> DRIPSTONE_LAMP = createBlock("dripstone_lamp", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DRIPSTONE_BLOCK).lightLevel((e) -> 8)));
    // Calcite
    public static final StoneBlockSet CALCITE_BRICKS = new StoneBlockSet(createBlock("calcite_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CALCITE))));
    public static final RegistryObject<Block> CALCITE_PILLAR = createBlock("calcite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE)));
    public static final RegistryObject<Block> CHISELED_CALCITE = createBlock("chiseled_calcite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CALCITE)));
    public static final RegistryObject<Block> HEAVY_CALCITE_BRICKS = createBlock("heavy_calcite_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE), BigBrickBlock.BrickType.CALCITE));
    public static final RegistryObject<Block> CALCITE_LAMP = createBlock("calcite_lamp", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CALCITE).lightLevel((e) -> 8)));
    // Tuff
    public static final StoneBlockSet TUFF_BRICKS = new StoneBlockSet(createBlock("tuff_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF))));
    public static final RegistryObject<Block> TUFF_PILLAR = createBlock("tuff_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.TUFF)));
    public static final RegistryObject<Block> CHISELED_TUFF = createBlock("chiseled_tuff", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));
    public static final RegistryObject<Block> HEAVY_TUFF_BRICKS = createBlock("heavy_tuff_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.copy(Blocks.TUFF), BigBrickBlock.BrickType.TUFF));
    public static final RegistryObject<Block> TUFF_LAMP = createBlock("tuff_lamp", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF).lightLevel((e) -> 8)));

    // Radioactive Crystals
    public static final RegistryObject<Block> HELIODOR_ROD = createBlock("heliodor_rod", () -> new GlassLikePillarBlock(APBlockProperties.NETHER_CRYSTAL));
    public static final RegistryObject<Block> EKANITE_ROD  = createBlock("ekanite_rod",  () -> new GlassLikePillarBlock(APBlockProperties.NETHER_CRYSTAL));
    public static final RegistryObject<Block> MONAZITE_ROD = createBlock("monazite_rod", () -> new GlassLikePillarBlock(APBlockProperties.NETHER_CRYSTAL));

    // Unobtanium
    public static final RegistryObject<Block> UNOBTANIUM_BLOCK = createBlock("unobtanium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)));

    // Nether Brass
    public static final StoneBlockSet NETHER_BRASS = new StoneBlockSet(createBlock("nether_brass_block", () -> new Block(APBlockProperties.NETHER_BRASS)));
    public static final StoneBlockSet CUT_NETHER_BRASS = new StoneBlockSet(createBlock("cut_nether_brass", () -> new Block(APBlockProperties.NETHER_BRASS)));
    public static final RegistryObject<Block> NETHER_BRASS_PILLAR = createBlock("nether_brass_pillar", () -> new RotatedPillarBlock(APBlockProperties.NETHER_BRASS));
    public static final StoneBlockSet SMOOTH_NETHER_BRASS = new StoneBlockSet(createBlock("smooth_nether_brass", () -> new Block(APBlockProperties.NETHER_BRASS)), NO_WALLS);
    public static final RegistryObject<Block> NETHER_BRASS_FIRE = createBlockNoItem("nether_brass_fire", () -> new GreenFireBlock(APBlockProperties.GREEN_FIRE));
    public static final RegistryObject<Block> NETHER_BRASS_CHAIN = createBlock("nether_brass_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(NETHER_BRASS.get()).sound(SoundType.CHAIN)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> NETHER_BRASS_LANTERN = createBlock("nether_brass_lantern", () -> new LanternBlock(BlockBehaviour.Properties.copy(NETHER_BRASS.get()).sound(SoundType.LANTERN).lightLevel((a)->13)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> NETHER_BRASS_TORCH = createBlockNoItem("nether_brass_torch", () -> new CustomTorchBlock(APBlockProperties.BRASS_TORCH, MiscRegistry.GREEN_FLAME));
    public static final RegistryObject<Block> NETHER_BRASS_WALL_TORCH = createBlockNoItem("nether_brass_wall_torch", () -> new CustomWallTorchBlock(APBlockProperties.BRASS_TORCH.dropsLike(NETHER_BRASS_TORCH.get()), MiscRegistry.GREEN_FLAME));

    public static final StoneBlockSet ESOTERRACK = new StoneBlockSet(createBlock("esoterrack", () -> new Block(APBlockProperties.ESOTERRACK)));
    public static final StoneBlockSet ESOTERRACK_BRICKS = new StoneBlockSet(createBlock("esoterrack_bricks", () -> new Block(APBlockProperties.ESOTERRACK)));
    public static final RegistryObject<Block> ESOTERRACK_PILLAR = createBlock("esoterrack_pillar", () -> new RotatedPillarBlock(APBlockProperties.ESOTERRACK));

    public static final StoneBlockSet ONYX = new StoneBlockSet(createBlock("onyx", () -> new Block(APBlockProperties.ONYX)));
    public static final StoneBlockSet ONYX_BRICKS = new StoneBlockSet(createBlock("onyx_bricks", () -> new Block(APBlockProperties.ONYX)));
    public static final RegistryObject<Block> ONYX_PILLAR = createBlock("onyx_pillar", () -> new RotatedPillarBlock(APBlockProperties.ONYX));


    private static RegistryObject<Block> createPottedPlant(RegistryObject<Block> plant) {
        String name = plant.getId().getPath();
        RegistryObject<Block> pot = BLOCKS.register("potted_" + name, () ->
                new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), plant, Block.Properties.copy(Blocks.POTTED_AZURE_BLUET))
        );
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(plant.getId(), pot);
        return pot;
    }

}