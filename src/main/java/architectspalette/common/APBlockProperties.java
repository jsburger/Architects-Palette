package architectspalette.common;

import architectspalette.common.blocks.CageLanternBlock;
import architectspalette.common.blocks.SunstoneBlock;
import architectspalette.common.blocks.abyssaline.AbyssalineHelper;
import architectspalette.common.blocks.abyssaline.AbyssalineLampBlock;
import architectspalette.common.blocks.abyssaline.ChiseledAbyssalineBlock;
import architectspalette.common.blocks.abyssaline.NewAbyssalineBlock;
import architectspalette.core.registry.APSounds;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class APBlockProperties {
	private static Block.Properties AbyssalineBase() {
		return Block.Properties.copy(Blocks.OBSIDIAN).strength(25.0F, 600.0F)
			.emissiveRendering(AbyssalineHelper::needsPostProcessing)
			.hasPostProcess(AbyssalineHelper::needsPostProcessing)
			.isValidSpawn(AbyssalineHelper::allowsMobSpawning)
			.lightLevel(NewAbyssalineBlock::getLightValue);
	}

	public static final Block.Properties ABYSSALINE = AbyssalineBase();

	public static final Block.Properties ABYSSALINE_LAMP = AbyssalineBase()
			.lightLevel(AbyssalineLampBlock::getLightValue);

	public static final Block.Properties CHISELED_ABYSSALINE = AbyssalineBase()
			.lightLevel(ChiseledAbyssalineBlock::getLightValue);


	public static Block.Properties Meat(MaterialColor color) {
		return Block.Properties.of(Material.VEGETABLE, color).strength(1.0F).sound(SoundType.CORAL_BLOCK);
	}

	public static final Block.Properties FLINT = Block.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).strength(3.0F, 16.0F).requiresCorrectToolForDrops();
	public static final Block.Properties MYONITE = Block.Properties.copy(Blocks.STONE);
	public static final Block.Properties SUNMETAL = Block.Properties.of(Material.METAL, MaterialColor.COLOR_BROWN).strength(2.0F, 8.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops();
	 // Should be less slippery?
	public static final Block.Properties BUILDING_ICE = Block.Properties.copy(Blocks.PACKED_ICE).friction(0.8F);
	 // As Prismarine
	public static final Block.Properties OLIVESTONE = Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_GREEN).strength(1.5F, 6.0F).requiresCorrectToolForDrops();
	 // As Nether Bricks
	public static final Block.Properties ALGAL_BRICK = Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_CYAN).strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops();

	public static final Block.Properties ENTWINE = Block.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).strength(3.0F, 6.0F).sound(APSounds.APSoundTypes.ENTWINE).requiresCorrectToolForDrops();
	public static final Block.Properties ENDER_PEARL = Block.Properties.of(Material.STONE, MaterialColor.COLOR_CYAN).strength(1.5F).sound(APSounds.APSoundTypes.ENDER_PEARL);
	public static final BlockBehaviour.Properties PLATING = BlockBehaviour.Properties.of(Material.METAL, MaterialColor.STONE).strength(4.0F, 10.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops();

	public static final BlockBehaviour.Properties MOLTEN_BRICK = Block.Properties.of(Material.STONE, MaterialColor.NETHER)
			.requiresCorrectToolForDrops()
			.strength(2.0F, 6.0F)
			.lightLevel((state) -> 3)
			.hasPostProcess((a, b, c) -> true)
			.emissiveRendering((a, b, c) -> true);

	public static final BlockBehaviour.Properties CAGE_LANTERN = BlockBehaviour.Properties.of(Material.METAL)
			.requiresCorrectToolForDrops()
			.emissiveRendering((state, reader, pos) -> state.getValue(CageLanternBlock.LIT))
			.hasPostProcess((state, reader, pos) -> state.getValue(CageLanternBlock.LIT))
			.strength(3.5f)
			.sound(SoundType.LANTERN)
			.noOcclusion();

	public static final BlockBehaviour.Properties ACACIA_TOTEM = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE)
			.strength(2.0F)
			.sound(SoundType.WOOD);

	// This makes a new property each time so that setting the door to not solid doesn't interfere.
	// That might not be a thing but I don't care to come up with a way of checking.
	public static BlockBehaviour.Properties TwistedWood() {
		return BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_PURPLE).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	}
	public static BlockBehaviour.Properties TwistedWood(boolean redstoneComponent) {
		BlockBehaviour.Properties p = TwistedWood();
		if (redstoneComponent) {
			return p.noCollission().strength(0.5f);
		}
		return p;
	}

	public static final BlockBehaviour.Properties SUNSTONE = BlockBehaviour.Properties.copy(Blocks.BASALT)
//			.setOpaque(SunstoneBlock::isOpaque).variableOpacity()
			.lightLevel(SunstoneBlock::lightValue);
			//Causes really wack lighting
//			.setEmmisiveRendering(SunstoneBlock::isLit).setNeedsPostProcessing(SunstoneBlock::isLit);

}