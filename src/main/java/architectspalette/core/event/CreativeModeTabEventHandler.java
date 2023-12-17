package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CreativeModeTabEventHandler {

    private static final List<Supplier<? extends ItemLike>> items = new ArrayList<>();
    private static final List<CreativeModeTab> tabs = new ArrayList<>();

    public static void assignItemToTab(Supplier<? extends ItemLike> item, CreativeModeTab... in_tabs) {
        for (CreativeModeTab tab : in_tabs) {
            items.add(item);
            tabs.add(tab);
        }
    }

    @SubscribeEvent
    public static void onCreativeTabRegister(CreativeModeTabEvent.BuildContents event) {
        int i = 0;
        for (Supplier<? extends ItemLike> item : items){
            if (event.getTab() == tabs.get(i)) {
                event.accept(item);
            }
            i++;
        }
        tabs.clear();
        items.clear();
    }


}
