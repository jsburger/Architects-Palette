package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TextureStitchEventHandler {

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS) {
            event.addSprite(new ResourceLocation(ArchitectsPalette.MOD_ID, "block/sheet_metal_block_ct"));
        }

    }
    @SubscribeEvent
    public static void onTextureStitchPost(TextureStitchEvent.Post event) {
        //SpriteShift.onTexturesDoneStitching();
    }
}
