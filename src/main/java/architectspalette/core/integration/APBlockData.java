package architectspalette.core.integration;

import architectspalette.core.registry.APBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import java.util.stream.Stream;

import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.util.DataUtils.registerFlammable;

public class APBlockData {

    public static void registerFlammables() {
        // Logs & Coal: 5, 5
        // Planks: 5, 20
        // Leaves & Wool: 30, 60
        // Plants: 60, 100
        registerFlammable(TWISTED_FENCE.get(), 5, 20);
        TWISTED_PLANKS.registerFlammable(5, 20);
        Stream.of(GRINNING_ACACIA_TOTEM, PLACID_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM,
                TWISTED_LOG, STRIPPED_TWISTED_LOG, TWISTED_WOOD, STRIPPED_TWISTED_WOOD,
                SPOOL, CHARCOAL_BLOCK,
                OAK_RAILING, OAK_BOARDS, BIRCH_BOARDS, BIRCH_RAILING, SPRUCE_BOARDS, SPRUCE_RAILING,
                ACACIA_BOARDS, ACACIA_RAILING, DARK_OAK_BOARDS, DARK_OAK_RAILING, JUNGLE_BOARDS, JUNGLE_RAILING,
                TWISTED_RAILING, TWISTED_BOARDS
                ).forEach((t) -> {
            registerFlammable(t.get(), 5, 5);
        });
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

        ItemBlockRenderTypes.setRenderLayer(HELIODOR_ROD.get(), RenderType.translucent());
    }

}