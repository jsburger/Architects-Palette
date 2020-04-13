package architectspalette.core.registry;

import architectspalette.common.APBlockProperties;
import architectspalette.common.blocks.*;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.util.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class APBlocks {
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ArchitectsPalette.MOD_ID);
	
	public static final RegistryObject<AbyssalineBlock> ABYSSALINE                         = RegistryUtils.createBlock("abyssaline", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<AbyssalineBlock> ABYSSALINE_BRICKS                  = RegistryUtils.createBlock("abyssaline_bricks", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<ChiseledAbyssalineBlock> CHISELED_ABYSSALINE_BRICKS = RegistryUtils.createBlock("chiseled_abyssaline_bricks", () -> new ChiseledAbyssalineBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<AbyssalinePillarBlock> ABYSSALINE_PILLAR            = RegistryUtils.createBlock("abyssaline_pillar", () -> new AbyssalinePillarBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
	public static final	RegistryObject<AbyssalineBrickStairsBlock> ABYSSALINE_BRICK_STAIRS = RegistryUtils.createBlock("abyssaline_brick_stairs", () -> new AbyssalineBrickStairsBlock(() -> ABYSSALINE_BRICKS.get().getDefaultState(), APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
	public static final	RegistryObject<AbyssalineBrickSlabBlock> ABYSSALINE_BRICK_SLAB     = RegistryUtils.createBlock("abyssaline_brick_slab", () -> new AbyssalineBrickSlabBlock(APBlockProperties.ABYSSALINE), ItemGroup.BUILDING_BLOCKS);
}