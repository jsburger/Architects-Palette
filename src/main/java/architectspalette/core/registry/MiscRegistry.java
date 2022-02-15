package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class MiscRegistry {

    public static final Tag.Named<Block> CRYSTAL_REPLACEABLE = BlockTags.bind(ArchitectsPalette.MOD_ID + ":" + "crystal_formation_replaceable");

}
