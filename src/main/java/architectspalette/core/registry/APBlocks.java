package architectspalette.core.registry;

import architectspalette.common.APBlockProperties;
import architectspalette.common.blocks.*;
import architectspalette.common.features.TwistedTree;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static architectspalette.core.registry.util.RegistryUtils.createBlock;
import static architectspalette.core.registry.util.RegistryUtils.createBlockNoItem;

public class APBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArchitectsPalette.MOD_ID);

    // Abyssaline
    public static final RegistryObject<AbyssalineBlock>            ABYSSALINE                 = createBlock("abyssaline",                 () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBlock>            ABYSSALINE_BRICKS          = createBlock("abyssaline_bricks",          () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<ChiseledAbyssalineBlock>    CHISELED_ABYSSALINE_BRICKS = createBlock("chiseled_abyssaline_bricks", () -> new ChiseledAbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalinePillarBlock>      ABYSSALINE_PILLAR          = createBlock("abyssaline_pillar",          () -> new AbyssalinePillarBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBrickStairsBlock> ABYSSALINE_BRICK_STAIRS    = createBlock("abyssaline_brick_stairs",    () -> new AbyssalineBrickStairsBlock(() -> ABYSSALINE_BRICKS.get().getDefaultState(), APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBrickSlabBlock>   ABYSSALINE_BRICK_SLAB      = createBlock("abyssaline_brick_slab",      () -> new AbyssalineBrickSlabBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    // public static final RegistryObject<AbyssalineBrickWallBlock>   ABYSSALINE_BRICK_WALL      = createBlock("abyssaline_brick_wall",      () -> new AbyssalineBrickWallBlock(APBlockProperties.ABYSSALINE), ItemGroup.DECORATIONS);

    // Villager Trade blocks
     // Funny fish blocks
    public static final RegistryObject<Block>    SALMON_LOG = createBlock("salmon_log",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.RED_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block>       COD_LOG = createBlock("cod_log",       () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> SALMON_SCALES = createBlock("salmon_scales", () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.RED_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block>    COD_SCALES = createBlock("cod_scales",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
     // Entrails
    public static final RegistryObject<Block> ENTRAILS = createBlock("entrails", () -> new Block(APBlockProperties.Meat(MaterialColor.PINK_TERRACOTTA)));
     // Plating & Piping
    public static final StoneBlockSet PLATING_BLOCK = new StoneBlockSet(createBlock("plating_block", () -> new Block(APBlockProperties.PLATING)));
    public static final RegistryObject<Block> PIPE = createBlock("pipe", () -> new PipeBlock(APBlockProperties.PLATING));
     //Spools
    public static final RegistryObject<Block> SPOOL = createBlock("spool", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.WHITE_WOOL)));

    // Charcoal Block
    public static final RegistryObject<Block> CHARCOAL_BLOCK = createBlockNoItem("charcoal_block", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.COAL_BLOCK)));

    // Limestone
    public static final StoneBlockSet LIMESTONE             = new StoneBlockSet(createBlock("limestone",              () -> new Block(APBlockProperties.LIMESTONE)));
    public static final StoneBlockSet LIMESTONE_BRICK       = new StoneBlockSet(createBlock("limestone_bricks",       () -> new Block(APBlockProperties.LIMESTONE)));
    public static final StoneBlockSet MUSHY_LIMESTONE_BRICK = new StoneBlockSet(createBlock("mushy_limestone_bricks", () -> new Block(APBlockProperties.LIMESTONE)));

    // Olivestone
    public static final StoneBlockSet OLIVESTONE_BRICK = new StoneBlockSet(createBlock("olivestone_bricks", () -> new Block(APBlockProperties.OLIVESTONE)));
    public static final StoneBlockSet OLIVESTONE_TILE  = new StoneBlockSet(createBlock("olivestone_tiles",  () -> new Block(APBlockProperties.OLIVESTONE)));

    public static final RegistryObject<Block> OLIVESTONE_PILLAR         = createBlock("olivestone_pillar", () -> new RotatedPillarBlock(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> CRACKED_OLIVESTONE_BRICKS = createBlock("cracked_olivestone_bricks", () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> CRACKED_OLIVESTONE_TILES  = createBlock("cracked_olivestone_tiles",  () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> CHISELED_OLIVESTONE       = createBlock("chiseled_olivestone", () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> ILLUMINATED_OLIVESTONE    = createBlock("illuminated_olivestone", () -> new Block(AbstractBlock.Properties.from(OLIVESTONE_BRICK.get()).setLightLevel((state) -> 15)));

    // Algal Brick
    public static final StoneBlockSet ALGAL_BRICK = new StoneBlockSet(createBlock("algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));
    public static final RegistryObject<Block> CRACKED_ALGAL_BRICKS  = createBlock("cracked_algal_bricks",  () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final RegistryObject<Block> CHISELED_ALGAL_BRICKS = createBlock("chiseled_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final StoneBlockSet OVERGROWN_ALGAL_BRICK = new StoneBlockSet(createBlock("overgrown_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));
    public static final RegistryObject<Block> ALGAL_LAMP = createBlock("algal_lamp", () -> new Block(AbstractBlock.Properties.from(Blocks.SEA_LANTERN)));

    // Ore Bricks
    public static final List<StoneBlockSet> ORE_BRICKS = addOreBricks();

    private static List<StoneBlockSet> addOreBricks() {
        List<String> ores = Arrays.asList("coal", "lapis", "redstone", "iron", "gold", "emerald", "diamond");
        List<StoneBlockSet> l = new LinkedList<>();
        ores.forEach((ore) -> {
                StoneBlockSet set = new StoneBlockSet(createBlock(ore + "_ore_bricks", () -> new Block(Block.Properties.from(Blocks.STONE_BRICKS))));
                createBlock("cracked_" + ore + "_ore_bricks", () -> new Block(Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
                createBlock("chiseled_" + ore + "_ore_bricks", () -> new Block(Block.Properties.from(Blocks.CHISELED_STONE_BRICKS)));
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
    public static final StoneBlockSet SUNMETAL = new StoneBlockSet(createBlock("sunmetal_block", () -> new Block(APBlockProperties.SUNMETAL)), false).addSlabs().addStairs();
    public static final RegistryObject<Block> CHISELED_SUNMETAL_BLOCK = createBlock("chiseled_sunmetal_block", () -> new Block(APBlockProperties.SUNMETAL));
    public static final RegistryObject<Block> SUNMETAL_PILLAR         = createBlock("sunmetal_pillar", () -> new RotatedPillarBlock(APBlockProperties.SUNMETAL));

    // Osseous Bricks
    public static final StoneBlockSet OSSEOUS_BRICK = new StoneBlockSet(createBlock("osseous_bricks", () -> new Block(Block.Properties.from(Blocks.BONE_BLOCK))));
    public static final RegistryObject<Block> OSSEOUS_PILLAR = createBlock("osseous_pillar", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.BONE_BLOCK)));
    // Withered
     // Todo: Replace bone block recipe to one that uses withered bone meal if that gets in
    public static final RegistryObject<Block> WITHERED_BONE_BLOCK = createBlock("withered_bone_block", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.BONE_BLOCK)));
    public static final StoneBlockSet      WITHERED_OSSEOUS_BRICK = new StoneBlockSet(createBlock("withered_osseous_bricks", () -> new Block(Block.Properties.from(Blocks.BONE_BLOCK))));
    public static final RegistryObject<Block> WITHERED_OSSEOUS_PILLAR = createBlock("withered_osseous_pillar", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.BONE_BLOCK)));
    // Wither Lamp
    public static final RegistryObject<Block> WITHER_LAMP = createBlock("wither_lamp", () -> new Block(AbstractBlock.Properties.from(Blocks.SEA_LANTERN)));

    // Entwine
    public static final StoneBlockSet ENTWINE = new StoneBlockSet(createBlock("entwine_block", () -> new Block(APBlockProperties.ENTWINE)), false).addSlabs().addStairs();
    public static final RegistryObject<Block> ENTWINE_PILLAR = createBlock("entwine_pillar", () -> new RotatedPillarBlock(APBlockProperties.ENTWINE));
    public static final RegistryObject<Block> CHISELED_ENTWINE = createBlock("chiseled_entwine", () -> new Block(APBlockProperties.ENTWINE));
    public static final RegistryObject<Block> ENTWINE_BARS = createBlock("entwine_bars", () -> new PaneBlock(AbstractBlock.Properties.from(ENTWINE.get()).notSolid()));

    // Heavy Stone Bricks
    public static final RegistryObject<Block> HEAVY_STONE_BRICKS = createBlock("heavy_stone_bricks", () -> new BigBrickBlock(AbstractBlock.Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> HEAVY_MOSSY_STONE_BRICKS = createBlock("heavy_mossy_stone_bricks", () -> new BigBrickBlock(AbstractBlock.Properties.from(Blocks.MOSSY_STONE_BRICKS)));
    public static final RegistryObject<Block> HEAVY_CRACKED_STONE_BRICKS = createBlock("heavy_cracked_stone_bricks", () -> new BigBrickBlock(AbstractBlock.Properties.from(Blocks.CRACKED_STONE_BRICKS)));

    // Polished Glowstone
    public static final StoneBlockSet POLISHED_GLOWSTONE = new StoneBlockSet(createBlock("polished_glowstone", () -> new Block(AbstractBlock.Properties.from(Blocks.GLOWSTONE))), false).addSlabs();
    public static final RegistryObject<Block> RUNIC_GLOWSTONE = createBlock("runic_glowstone", () -> new Block(AbstractBlock.Properties.from(Blocks.GLOWSTONE)));

    // Scute Block
    public static final RegistryObject<Block> SCUTE_BLOCK = createBlock("scute_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIME).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.BASALT)));
    // Rotten Flesh Block
    public static final RegistryObject<Block> ROTTEN_FLESH_BLOCK = createBlock("rotten_flesh_block", () -> new Block(APBlockProperties.Meat(MaterialColor.ADOBE)));

    // Gilded Sandstone
    public static final StoneBlockSet GILDED_SANDSTONE = new StoneBlockSet(createBlock("gilded_sandstone", () -> new Block(AbstractBlock.Properties.from(Blocks.SANDSTONE))), false).addSlabs().addStairs();
    public static final RegistryObject<Block> GILDED_SANDSTONE_PILLAR = createBlock("gilded_sandstone_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> CHISELED_GILDED_SANDSTONE = createBlock("chiseled_gilded_sandstone", () -> new Block(AbstractBlock.Properties.from(Blocks.SANDSTONE)));

    // Mossy Blackstone Variants
    public static final RegistryObject<Block> WEEPING_BLACKSTONE = createBlock("weeping_blackstone", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACKSTONE)));
    public static final RegistryObject<Block> TWISTING_BLACKSTONE = createBlock("twisting_blackstone", () -> new Block(AbstractBlock.Properties.from(Blocks.BLACKSTONE)));
    public static final RegistryObject<Block> WEEPING_BLACKSTONE_BRICKS = createBlock("weeping_blackstone_bricks", () -> new Block(AbstractBlock.Properties.from(Blocks.POLISHED_BLACKSTONE_BRICKS)));
    public static final RegistryObject<Block> TWISTING_BLACKSTONE_BRICKS = createBlock("twisting_blackstone_bricks", () -> new Block(AbstractBlock.Properties.from(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    // End Stone Variants
    public static final RegistryObject<Block>   CHORAL_END_STONE_BRICKS = createBlock("choral_end_stone_bricks",   () -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE_BRICKS)));
    public static final RegistryObject<Block>  CRACKED_END_STONE_BRICKS = createBlock("cracked_end_stone_bricks",  () -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE_BRICKS)));
    public static final RegistryObject<Block> CHISELED_END_STONE_BRICKS = createBlock("chiseled_end_stone_bricks", () -> new Block(AbstractBlock.Properties.from(Blocks.END_STONE_BRICKS)));

    // Warpstone
    public static final StoneBlockSet WARPSTONE = new StoneBlockSet(createBlock("warpstone", () -> new Block(AbstractBlock.Properties.from(Blocks.STONE))));

    // Twisted Wood
     // Todo: Bookshelf, sign(?), boat(?), tree gen
    public static final StoneBlockSet TWISTED_PLANKS = new StoneBlockSet(createBlock("twisted_planks", () -> new Block(APBlockProperties.TwistedWood())), false).addSlabs().addStairs();

    public static final RegistryObject<Block>           TWISTED_LOG = createBlock("twisted_log",          () -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final RegistryObject<Block>          TWISTED_WOOD = createBlock("twisted_wood",         () -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final RegistryObject<Block>  STRIPPED_TWISTED_LOG = createBlock("stripped_twisted_log", () -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final RegistryObject<Block> STRIPPED_TWISTED_WOOD = createBlock("stripped_twisted_wood",() -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final RegistryObject<Block>        TWISTED_LEAVES = createBlock("twisted_leaves",       () -> new LeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block>         TWISTED_FENCE = createBlock("twisted_fence",        () -> new         FenceBlock(APBlockProperties.TwistedWood()), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block>    TWISTED_FENCE_GATE = createBlock("twisted_fence_gate",   () -> new     FenceGateBlock(APBlockProperties.TwistedWood()), ItemGroup.REDSTONE);
    public static final RegistryObject<Block>          TWISTED_DOOR = createBlock("twisted_door",         () -> new          DoorBlock(APBlockProperties.TwistedWood().notSolid()), ItemGroup.REDSTONE);
    public static final RegistryObject<Block>      TWISTED_TRAPDOOR = createBlock("twisted_trapdoor",     () -> new      TrapDoorBlock(APBlockProperties.TwistedWood().notSolid()), ItemGroup.REDSTONE);
    public static final RegistryObject<Block>        TWISTED_BUTTON = createBlock("twisted_button",       () -> new    WoodButtonBlock(APBlockProperties.TwistedWood(true)), ItemGroup.REDSTONE);
    public static final RegistryObject<Block> TWISTED_PRESSURE_PLATE = createBlock("twisted_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, APBlockProperties.TwistedWood(true)), ItemGroup.REDSTONE);
    public static final RegistryObject<Block>        TWISTED_SAPLING = createBlock("twisted_sapling", () -> new SaplingBlock(new TwistedTree(), AbstractBlock.Properties.from(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> POTTED_TWISTED_SAPLING = createPottedPlant(TWISTED_SAPLING);

    // Basalt Tiles
    public static final StoneBlockSet BASALT_TILES = new StoneBlockSet(createBlock("basalt_tiles", () -> new Block(AbstractBlock.Properties.from(Blocks.BASALT))));
    public static final RegistryObject<Block>  CRACKED_BASALT_TILES = createBlock("cracked_basalt_tiles",  () -> new Block(AbstractBlock.Properties.from(Blocks.BASALT)));
    public static final RegistryObject<Block> CHISELED_BASALT_TILES = createBlock("chiseled_basalt_tiles", () -> new Block(AbstractBlock.Properties.from(Blocks.BASALT)));

    // Celestial Stones
    public static final RegistryObject<Block> SUNSTONE  = createBlock("sunstone",  () -> new SunstoneBlock(AbstractBlock.Properties.from(Blocks.BASALT).notSolid(), SunstoneBlock::sunstoneLight));
    public static final RegistryObject<Block> MOONSTONE = createBlock("moonstone", () -> new SunstoneBlock(AbstractBlock.Properties.from(Blocks.BASALT).notSolid(), SunstoneBlock::moonstoneLight));

    // Odd block variants
    public static final RegistryObject<Block> MOLTEN_NETHER_BRICKS = createBlock("molten_nether_bricks", () -> new Block(APBlockProperties.MOLTEN_BRICK));
    public static final RegistryObject<Block> COARSE_SNOW = createBlock("coarse_snow", () -> new Block(AbstractBlock.Properties.from(Blocks.SNOW_BLOCK)));


    private static RegistryObject<Block> createPottedPlant(RegistryObject<Block> plant) {
        String name = plant.getId().getPath();
        Block pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), plant, Block.Properties.from(Blocks.POTTED_AZURE_BLUET));
        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(plant.getId(), () -> pot);
        return BLOCKS.register("potted_" + name, () -> pot);
    }

}