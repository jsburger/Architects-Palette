package architectspalette.common;

import architectspalette.common.blocks.ChiseledAbyssalineBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class APBlockProperties {
	public static final Block.Properties ABYSSALINE 			= Block.Properties.from(Blocks.OBSIDIAN).hardnessAndResistance(25.0F, 600.0F);
	public static final Block.Properties CHISELED_ABYSSALINE 	= Block.Properties.from(Blocks.OBSIDIAN).hardnessAndResistance(25.0F, 600.0F).setEmmisiveRendering(APBlockProperties::setEmmisiveRendering).setNeedsPostProcessing(APBlockProperties::needsPostProcessing);

	public static Block.Properties Meat(MaterialColor color) {
		return Block.Properties.create(Material.GOURD, color).hardnessAndResistance(1.0F).sound(SoundType.CORAL);
	}

	public static final Block.Properties FLINT = Block.Properties.create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(3.0F, 16.0F);
	public static final Block.Properties LIMESTONE = Block.Properties.from(Blocks.STONE);
	public static final Block.Properties SUNMETAL = Block.Properties.create(Material.IRON, MaterialColor.BROWN).hardnessAndResistance(2.0F, 8.0F).sound(SoundType.NETHERITE);
	 // Should be less slippery?
	public static final Block.Properties BUILDING_ICE = Block.Properties.from(Blocks.PACKED_ICE).slipperiness(0.8F);
	 // As Prismarine
	public static final Block.Properties OLIVESTONE = Block.Properties.create(Material.ROCK, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(1.5F, 6.0F);
	 // As Nether Bricks
	public static final Block.Properties ALGAL_BRICK = Block.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.NETHER_BRICK);

	public static final Block.Properties ENTWINE = Block.Properties.create(Material.ROCK, MaterialColor.CYAN).hardnessAndResistance(3.0F, 6.0F)/*.sound(APSounds.APSoundTypes.ENTWINE)*/;
	public static final AbstractBlock.Properties PLATING = AbstractBlock.Properties.create(Material.IRON, MaterialColor.STONE).hardnessAndResistance(4.0F, 10.0F).sound(SoundType.NETHERITE);

	public static final AbstractBlock.Properties MOLTEN_BRICK = Block.Properties.create(Material.ROCK, MaterialColor.NETHERRACK)
			.setRequiresTool()
			.hardnessAndResistance(2.0F, 6.0F)
			.setLightLevel((state) -> {return 3;})
			.setNeedsPostProcessing((a, b, c) -> true)
			.setEmmisiveRendering((a, b, c) -> true);

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

	private static boolean setEmmisiveRendering(BlockState state, IBlockReader reader, BlockPos pos) {
		return state.get(ChiseledAbyssalineBlock.LIGHT) / 2 > 4;
	}
	
	private static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos) {
		int light = state.get(ChiseledAbyssalineBlock.LIGHT) / 2;
		return light < 4;
	}
}