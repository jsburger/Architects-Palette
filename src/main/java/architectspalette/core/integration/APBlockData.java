package architectspalette.core.integration;

import architectspalette.core.registry.APBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

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

    public static void setupRenderLayers() {
        RenderTypeLookup.setRenderLayer(APBlocks.ENTWINE_BARS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.SUNMETAL_BARS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(APBlocks.TWISTED_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.TWISTED_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.TWISTED_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.POTTED_TWISTED_SAPLING.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(APBlocks.REDSTONE_CAGE_LANTERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.GLOWSTONE_CAGE_LANTERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.ALGAL_CAGE_LANTERN.get(), RenderType.getCutout());
    }
}
