package architectspalette.core.registry.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class DataUtils {

    public static void registerFlammable(Block block, Integer encouragement, Integer flammability) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, encouragement, flammability);
    }

    public static void registerStrippable(Block log, Block stripped) {
//        AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
//        AxeItem.STRIPABLES.put(log, stripped);
    }


}
