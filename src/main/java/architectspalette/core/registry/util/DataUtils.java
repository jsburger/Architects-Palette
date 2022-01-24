package architectspalette.core.registry.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class DataUtils {

    static FireBlock fire = (FireBlock) Blocks.FIRE;
    public static void registerFlammable(Block block, Integer encouragement, Integer flammability) {
        fire.setFlammable(block, encouragement, flammability);
    }


}
