package architectspalette.common;

import architectspalette.common.blocks.CageLanternBlock;
import architectspalette.common.blocks.SunstoneBlock;
import architectspalette.common.blocks.abyssaline.AbyssalineHelper;
import architectspalette.core.registry.APSounds;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class APBlockProperties {
	public static final Block.Properties ABYSSALINE = Block.Properties.from(Blocks.OBSIDIAN).hardnessAndResistance(25.0F, 600.0F)
			.setEmmisiveRendering(AbyssalineHelper::needsPostProcessing)
			.setNeedsPostProcessing(AbyssalineHelper::needsPostProcessing)
			.setAllowsSpawn(AbyssalineHelper::allowsMobSpawning);

	public static Block.Properties Meat(MaterialColor color) {
		return Block.Properties.create(Material.GOURD, color).hardnessAndResistance(1.0F).sound(SoundType.CORAL);
	}

	public static final Block.Properties FLINT = Block.Properties.create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(3.0F, 16.0F).setRequiresTool();
	public static final Block.Properties LIMESTONE = Block.Properties.from(Blocks.STONE);
	public static final Block.Properties SUNMETAL = Block.Properties.create(Material.IRON, MaterialColor.BROWN).hardnessAndResistance(2.0F, 8.0F).sound(SoundType.NETHERITE).setRequiresTool();
	 // Should be less slippery?
	public static final Block.Properties BUILDING_ICE = Block.Properties.from(Blocks.PACKED_ICE).slipperiness(0.8F);
	 // As Prismarine
	public static final Block.Properties OLIVESTONE = Block.Properties.create(Material.ROCK, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(1.5F, 6.0F).setRequiresTool();
	 // As Nether Bricks
	public static final Block.Properties ALGAL_BRICK = Block.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.NETHER_BRICK).setRequiresTool();

	public static final Block.Properties ENTWINE = Block.Properties.create(Material.ROCK, MaterialColor.CYAN).hardnessAndResistance(3.0F, 6.0F).sound(APSounds.APSoundTypes.ENTWINE).setRequiresTool();
	public static final Block.Properties ENDER_PEARL = Block.Properties.create(Material.ROCK, MaterialColor.CYAN).hardnessAndResistance(1.5F).sound(APSounds.APSoundTypes.ENDER_PEARL);
	public static final AbstractBlock.Properties PLATING = AbstractBlock.Properties.create(Material.IRON, MaterialColor.STONE).hardnessAndResistance(4.0F, 10.0F).sound(SoundType.NETHERITE).setRequiresTool();

	public static final AbstractBlock.Properties MOLTEN_BRICK = Block.Properties.create(Material.ROCK, MaterialColor.NETHERRACK)
			.setRequiresTool()
			.hardnessAndResistance(2.0F, 6.0F)
			.setLightLevel((state) -> 3)
			.setNeedsPostProcessing((a, b, c) -> true)
			.setEmmisiveRendering((a, b, c) -> true);

	public static final AbstractBlock.Properties CAGE_LANTERN = AbstractBlock.Properties.create(Material.IRON)
			.setRequiresTool()
			.setEmmisiveRendering((state, reader, pos) -> state.get(CageLanternBlock.LIT))
			.setNeedsPostProcessing((state, reader, pos) -> state.get(CageLanternBlock.LIT))
			.hardnessAndResistance(3.5f)
			.sound(SoundType.LANTERN)
			.notSolid();

	public static final AbstractBlock.Properties ACACIA_TOTEM = AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE)
			.hardnessAndResistance(2.0F)
			.sound(SoundType.WOOD);

	// This makes a new property each time so that setting the door to not solid doesn't interfere.
	// That might not be a thing but I don't care to come up with a way of checking.
	public static AbstractBlock.Properties TwistedWood() {
		return AbstractBlock.Properties.create(Material.WOOD, MaterialColor.PURPLE).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	}
	public static AbstractBlock.Properties TwistedWood(boolean redstoneComponent) {
		AbstractBlock.Properties p = TwistedWood();
		if (redstoneComponent) {
			return p.doesNotBlockMovement().hardnessAndResistance(0.5f);
		}
		return p;
	}

	public static final AbstractBlock.Properties SUNSTONE = AbstractBlock.Properties.from(Blocks.BASALT)
//			.setOpaque(SunstoneBlock::isOpaque).variableOpacity()
			.setLightLevel(SunstoneBlock::lightValue);
			//Causes really wack lighting
//			.setEmmisiveRendering(SunstoneBlock::isLit).setNeedsPostProcessing(SunstoneBlock::isLit);

}