package architectspalette.core.integration;

import architectspalette.core.registry.APBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

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
        RenderTypeLookup.setRenderLayer(APBlocks.ENTWINE_BARS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.SUNMETAL_BARS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(APBlocks.TWISTED_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.TWISTED_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.TWISTED_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.POTTED_TWISTED_SAPLING.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(APBlocks.REDSTONE_CAGE_LANTERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.GLOWSTONE_CAGE_LANTERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(APBlocks.ALGAL_CAGE_LANTERN.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(APBlocks.ACACIA_TOTEM_WING.get(), RenderType.getCutout());
    }

    public static void registerStrippables() {
        registerStrippable(TWISTED_LOG.get(), STRIPPED_TWISTED_LOG.get());
        registerStrippable(TWISTED_WOOD.get(), STRIPPED_TWISTED_WOOD.get());

        registerStrippable(Blocks.STRIPPED_ACACIA_LOG, APBlocks.GRINNING_ACACIA_TOTEM.get());
        registerStrippable(GRINNING_ACACIA_TOTEM.get(), PLACID_ACACIA_TOTEM.get());
        registerStrippable(PLACID_ACACIA_TOTEM.get(), SHOCKED_ACACIA_TOTEM.get());
        registerStrippable(SHOCKED_ACACIA_TOTEM.get(), GRINNING_ACACIA_TOTEM.get());
    }

}