package architectspalette.core.registry.util;

import architectspalette.content.blocks.abyssaline.AbyssalineBlock;
import architectspalette.core.registry.APBlockProperties;
import architectspalette.core.registry.util.BlockNode.DataFlags;
import architectspalette.core.registry.util.BlockNode.Style;
import architectspalette.core.registry.util.BlockNode.Tool;

import static architectspalette.core.registry.util.BlockNode.BlockType.*;
import static architectspalette.core.registry.util.RegistryUtils.createBlock;

public class TreeExample {

    public static BlockNode ABYSSALINE = new BlockNode.Builder()
            .tool(Tool.DIAMOND_PICK)
            .style(Style.TOP_SIDES)
            .flag(DataFlags.ABYSSALINE)
            .base(createBlock("abyssaline", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE)))
            .bricks((b -> { //Bricks
                b.slabs();  //With slabs
                b.addPart(CHISELED); //And a chiseled variant
            }))
            .tiles(BlockNode.Builder::slabs) //Tiles w/ slabs
            .variants(PILLAR, NUB, PLATING) //Generic variants
            .build();

}
