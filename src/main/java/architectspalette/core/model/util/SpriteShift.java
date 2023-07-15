package architectspalette.core.model.util;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SpriteShift {

    private static final Map<String, SpriteShift> entries = new HashMap<>();
    private static Boolean areTexturesReady = false;
    //I wasn't going to copy this part from Create, but I now know why they needed it.
    public static SpriteShift getShift(ResourceLocation from_block, ResourceLocation to_block) {
        String key = from_block.toString() + "->" + to_block.toString();
        if (entries.containsKey(key)) {
            return entries.get(key);
        }
        SpriteShift shift = new SpriteShift(from_block, to_block);
        entries.put(key, shift);
        return shift;
    }
    public static SpriteShift getShift(String from_block, String to_block) {
        return getShift(new ResourceLocation(ArchitectsPalette.MOD_ID, from_block), new ResourceLocation(ArchitectsPalette.MOD_ID, to_block));
    }

    public static void onTexturesDoneStitching() {
        areTexturesReady = true;
        for (SpriteShift entry : entries.values()) {
            entry.init();
        }
    }

    protected TextureAtlasSprite from;
    protected TextureAtlasSprite to;
    protected ResourceLocation fromLocation;
    protected ResourceLocation toLocation;

    private SpriteShift(ResourceLocation from_block, ResourceLocation to_block) {
        fromLocation = from_block;
        toLocation = to_block;
        if (areTexturesReady) {
            init();
        }
    }
    private void init() {
        Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
        from = atlas.apply(fromLocation);
        to = atlas.apply(toLocation);
    }

    //Could optimize by saving this value. Hell, should probably just not save the sprites.
    public float getUShift() {
        return to.getU0() - from.getU0();
    }

    public float getVShift() {
        return to.getV0() - from.getV0();
    }

    public float getVHeight() {
        return from.getV1() - from.getV0();
    }

}
