package architectspalette.core.registry.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class DataUtils {

    public static void registerFlammable(Block block, Integer encouragement, Integer flammability) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFireInfo(block, encouragement, flammability);
    }

}
