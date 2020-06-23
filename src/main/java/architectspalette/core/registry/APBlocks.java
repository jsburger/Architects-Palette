package architectspalette.core.registry;

import architectspalette.common.APBlockProperties;
import architectspalette.common.blocks.*;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.RegistryUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class APBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ArchitectsPalette.MOD_ID);

    // Abyssaline
    public static final RegistryObject<AbyssalineBlock>                          ABYSSALINE = RegistryUtils.createBlock("abyssaline",                 () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBlock>                   ABYSSALINE_BRICKS = RegistryUtils.createBlock("abyssaline_bricks",          () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<ChiseledAbyssalineBlock>  CHISELED_ABYSSALINE_BRICKS = RegistryUtils.createBlock("chiseled_abyssaline_bricks", () -> new ChiseledAbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalinePillarBlock>             ABYSSALINE_PILLAR = RegistryUtils.createBlock("abyssaline_pillar",          () -> new AbyssalinePillarBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBrickStairsBlock>  ABYSSALINE_BRICK_STAIRS = RegistryUtils.createBlock("abyssaline_brick_stairs",    () -> new AbyssalineBrickStairsBlock(() -> ABYSSALINE_BRICKS.get().getDefaultState(), APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<AbyssalineBrickSlabBlock>      ABYSSALINE_BRICK_SLAB = RegistryUtils.createBlock("abyssaline_brick_slab",      () -> new AbyssalineBrickSlabBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);

    // Funny fish blocks
    public static final RegistryObject<Block>    SALMON_LOG = RegistryUtils.createBlock("salmon_log",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.RED_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block>       COD_LOG = RegistryUtils.createBlock("cod_log",       () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> SALMON_SCALES = RegistryUtils.createBlock("salmon_scales", () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.RED_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block>    COD_SCALES = RegistryUtils.createBlock("cod_scales",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.BUILDING_BLOCKS);

    // Charcoal Block
    public static final RegistryObject<Block> CHARCOAL_BLOCK = RegistryUtils.createBlockNoItem("charcoal_block", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.COAL_BLOCK)));

    // Limestone
    public static final RegistryObject<Block> LIMESTONE                      = RegistryUtils.createBlock("limestone",                    () -> new Block(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   LIMESTONE_SLAB               = RegistryUtils.createBlock("limestone_slab",               () -> new SlabBlock(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   LIMESTONE_STAIRS             = RegistryUtils.createBlock("limestone_stairs",             () -> new StairsBlock(() -> LIMESTONE.get().getDefaultState(), APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   LIMESTONE_WALL               = RegistryUtils.createBlock("limestone_wall",               () -> new WallBlock(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block> LIMESTONE_BRICKS               = RegistryUtils.createBlock("limestone_bricks",             () -> new Block(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   LIMESTONE_BRICK_SLAB         = RegistryUtils.createBlock("limestone_brick_slab",         () -> new SlabBlock(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   LIMESTONE_BRICK_STAIRS       = RegistryUtils.createBlock("limestone_brick_stairs",       () -> new StairsBlock(() -> LIMESTONE_BRICKS.get().getDefaultState(), APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   LIMESTONE_BRICK_WALL         = RegistryUtils.createBlock("limestone_brick_wall",         () -> new WallBlock(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block> MUSHY_LIMESTONE_BRICKS         = RegistryUtils.createBlock("mushy_limestone_bricks",       () -> new Block(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   MUSHY_LIMESTONE_BRICK_SLAB   = RegistryUtils.createBlock("mushy_limestone_brick_slab",   () -> new SlabBlock(APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   MUSHY_LIMESTONE_BRICK_STAIRS = RegistryUtils.createBlock("mushy_limestone_brick_stairs", () -> new StairsBlock(() -> MUSHY_LIMESTONE_BRICKS.get().getDefaultState(), APBlockProperties.LIMESTONE));
    public static final RegistryObject<Block>   MUSHY_LIMESTONE_BRICK_WALL   = RegistryUtils.createBlock("mushy_limestone_brick_wall",   () -> new WallBlock(APBlockProperties.LIMESTONE));

    // Olivestone
    public static final RegistryObject<Block> OLIVESTONE_BRICKS           = RegistryUtils.createBlock("olivestone_bricks",         () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   CRACKED_OLIVESTONE_BRICKS = RegistryUtils.createBlock("cracked_olivestone_bricks", () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   OLIVESTONE_BRICK_SLAB     = RegistryUtils.createBlock("olivestone_brick_slab",     () -> new SlabBlock(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   OLIVESTONE_BRICK_STAIRS   = RegistryUtils.createBlock("olivestone_brick_stairs",   () -> new StairsBlock(() -> OLIVESTONE_BRICKS.get().getDefaultState(), APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   OLIVESTONE_BRICK_WALL     = RegistryUtils.createBlock("olivestone_brick_wall",     () -> new WallBlock(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> OLIVESTONE_TILES            = RegistryUtils.createBlock("olivestone_tiles",          () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   CRACKED_OLIVESTONE_TILES  = RegistryUtils.createBlock("cracked_olivestone_tiles",  () -> new Block(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   OLIVESTONE_TILE_SLAB      = RegistryUtils.createBlock("olivestone_tile_slab",      () -> new SlabBlock(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   OLIVESTONE_TILE_STAIRS    = RegistryUtils.createBlock("olivestone_tile_stairs",    () -> new StairsBlock(() -> OLIVESTONE_TILES.get().getDefaultState(),APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block>   OLIVESTONE_TILE_WALL      = RegistryUtils.createBlock("olivestone_tile_wall",      () -> new WallBlock(APBlockProperties.OLIVESTONE));
    public static final RegistryObject<Block> OLIVESTONE_PILLAR           = RegistryUtils.createBlock("olivestone_pillar",         () -> new RotatedPillarBlock(APBlockProperties.OLIVESTONE));




}