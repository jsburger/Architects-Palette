package architectspalette.common;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class APBlockProperties {
	public static final Block.Properties ABYSSALINE = Block.Properties.from(Blocks.OBSIDIAN).hardnessAndResistance(25.0F, 600.0F);

	public static Block.Properties Meat(MaterialColor color) {
		return Block.Properties.create(Material.GOURD, color).hardnessAndResistance(1.0F).sound(SoundType.CORAL);
	}

}