package architectspalette.core.model.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.function.Function;

public class SpriteShift {

    protected TextureAtlasSprite from;
    protected TextureAtlasSprite to;

    public SpriteShift(ResourceLocation from_block, ResourceLocation to_block) {
        Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
        from = atlas.apply(from_block);
        to = atlas.apply(to_block);
    }

    public float getUShift() {
        return to.getU0() - from.getU0();
    }

    public float getVShift() {
        return to.getV0() - from.getV0();
    }

}
