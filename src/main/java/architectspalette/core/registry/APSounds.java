package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class APSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ArchitectsPalette.MOD_ID);

    private static RegistryObject<SoundEvent> register(String key) {
        return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(ArchitectsPalette.MOD_ID, key)));
    }

    public static final RegistryObject<SoundEvent> BLOCK_ENTWINE_PLACE = register("block.entwine.place");
    public static final RegistryObject<SoundEvent> BLOCK_ENTWINE_BREAK = register("block.entwine.break");

    public static class APSoundTypes {
        public static final SoundType ENTWINE = new SoundType(1.0F, 1.0F, APSounds.BLOCK_ENTWINE_BREAK.get(), SoundEvents.BLOCK_GLASS_STEP, APSounds.BLOCK_ENTWINE_PLACE.get(), SoundEvents.BLOCK_GLASS_HIT, SoundEvents.BLOCK_GLASS_FALL);
    }
}
