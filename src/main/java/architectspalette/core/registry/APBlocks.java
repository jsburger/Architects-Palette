package architectspalette.core.registry;

import architectspalette.common.APBlockProperties;
import architectspalette.common.blocks.*;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
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
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ArchitectsPalette.MOD_ID);

    // Abyssaline
    public static final RegistryObject<AbyssalineBlock>            ABYSSALINE                 = createBlock("abyssaline",                 () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBlock>            ABYSSALINE_BRICKS          = createBlock("abyssaline_bricks",          () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<ChiseledAbyssalineBlock>    CHISELED_ABYSSALINE_BRICKS = createBlock("chiseled_abyssaline_bricks", () -> new ChiseledAbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalinePillarBlock>      ABYSSALINE_PILLAR          = createBlock("abyssaline_pillar",          () -> new AbyssalinePillarBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBrickStairsBlock> ABYSSALINE_BRICK_STAIRS    = createBlock("abyssaline_brick_stairs",    () -> new AbyssalineBrickStairsBlock(() -> ABYSSALINE_BRICKS.get().getDefaultState(), APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBrickSlabBlock>   ABYSSALINE_BRICK_SLAB      = createBlock("abyssaline_brick_slab",      () -> new AbyssalineBrickSlabBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);

    // Funny fish blocks
    public static final RegistryObject<Block>    SALMON_LOG = createBlock("salmon_log",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.RED_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block>       COD_LOG = createBlock("cod_log",       () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> SALMON_SCALES = createBlock("salmon_scales", () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.RED_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block>    COD_SCALES = createBlock("cod_scales",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);

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

    // Algal Brick
    public static final StoneBlockSet ALGAL_BRICK = new StoneBlockSet(createBlock("algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));
    public static final RegistryObject<Block> CRACKED_ALGAL_BRICKS  = createBlock("cracked_algal_bricks",  () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final RegistryObject<Block> CHISELED_ALGAL_BRICKS = createBlock("chiseled_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final StoneBlockSet OVERGROWN_ALGAL_BRICK = new StoneBlockSet(createBlock("overgrown_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));

    // Ore Bricks
    public static final List<StoneBlockSet> ORE_BRICKS = addOreBricks();

    private static List<StoneBlockSet> addOreBricks() {
        List<String> ores = Arrays.asList("coal", "lapis", "redstone", "iron", "gold", "emerald", "diamond");
        List<StoneBlockSet> l = new LinkedList<>();
        ores.forEach((ore) -> {
                StoneBlockSet set = new StoneBlockSet(createBlock(ore + "_ore_bricks", () -> new Block(Block.Properties.from(Blocks.STONE_BRICKS))), false);
                createBlock("cracked_" + ore + "_ore_bricks", () -> new Block(Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
                createBlock("chiseled_" + ore + "_ore_bricks", () -> new Block(Block.Properties.from(Blocks.CHISELED_STONE_BRICKS)));
                l.add(set.addSlabs().addWalls());
            }
        );
        return l;
    }

    // Flint Blocks
    public static final RegistryObject<Block> FLINT_BLOCK  = createBlock("flint_block",  () -> new FlintBlock(APBlockProperties.FLINT));
    public static final RegistryObject<Block> FLINT_TILES  = createBlock("flint_tiles",  () -> new FlintBlock(APBlockProperties.FLINT));
    public static final RegistryObject<Block> FLINT_PILLAR = createBlock("flint_pillar", () -> new FlintPillarBlock(APBlockProperties.FLINT));


}