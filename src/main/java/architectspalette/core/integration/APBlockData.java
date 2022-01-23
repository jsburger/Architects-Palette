package architectspalette.core.integration;

import architectspalette.core.registry.APBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.util.DataUtils.registerFlammable;
import static architectspalette.core.registry.util.DataUtils.registerStrippable;

public class APBlockData {

    public static void registerFlammables() {
        // Logs & Coal: 5, 5
        // Planks: 5, 20
        // Leaves & Wool: 30, 60
        // Plants: 60, 100
        registerFlammable(CHARCOAL_BLOCK.get(), 5, 5);
    }

    public static void setupRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(APBlocks.ENTWINE_BARS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocks.SUNMETAL_BARS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(APBlocks.TWISTED_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocks.TWISTED_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocks.TWISTED_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocks.POTTED_TWISTED_SAPLING.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(APBlocks.REDSTONE_CAGE_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocks.GLOWSTONE_CAGE_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocks.ALGAL_CAGE_LANTERN.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(APBlocks.ACACIA_TOTEM_WING.get(), RenderType.cutout());
    }

    public static void registerStrippables() {
        registerStrippable(TWISTED_LOG.get(), STRIPPED_TWISTED_LOG.get());
        registerStrippable(TWISTED_WOOD.get(), STRIPPED_TWISTED_WOOD.get());
    }

}