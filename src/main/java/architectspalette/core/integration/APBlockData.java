package architectspalette.core.integration;

import static architectspalette.core.registry.APBlocks.CHARCOAL_BLOCK;
import static architectspalette.core.registry.util.DataUtils.registerFlammable;

public class APBlockData {

    public static void registerFlammables() {
        // Logs & Coal: 5, 5
        // Planks: 5, 20
        // Leaves & Wool: 30, 60
        // Plants: 60, 100
        registerFlammable(CHARCOAL_BLOCK.get(), 5, 5);
    }

}
