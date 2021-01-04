package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.block.SoundType;
import net.minecraft.util.LazyValue;
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

    public static final RegistryObject<SoundEvent> ITEM_WARPS = register("block.warping.item_warps");
    public static final RegistryObject<SoundEvent> CAGE_LANTERN_TOGGLE_ON = register("block.cage_lantern.toggle_on");
    public static final RegistryObject<SoundEvent> CAGE_LANTERN_TOGGLE_OFF = register("block.cage_lantern.toggle_off");

    public static class APSoundTypes {
        public static final SoundType ENTWINE = new LazySoundType(
                1.0F, 1.0F,
                new LazyValue<SoundEvent>(BLOCK_ENTWINE_BREAK),
                new LazyValue<SoundEvent>(() -> SoundEvents.BLOCK_GLASS_STEP),
                new LazyValue<SoundEvent>(BLOCK_ENTWINE_PLACE),
                new LazyValue<SoundEvent>(() -> SoundEvents.BLOCK_GLASS_HIT),
                new LazyValue<SoundEvent>(() -> SoundEvents.BLOCK_GLASS_FALL)
        );
    }

    private static class LazySoundType extends SoundType {
        private final LazyValue<SoundEvent> breakSound;
        private final LazyValue<SoundEvent> stepSound;
        private final LazyValue<SoundEvent> placeSound;
        private final LazyValue<SoundEvent> hitSound;
        private final LazyValue<SoundEvent> fallSound;

        public LazySoundType(float volumeIn, float pitchIn, LazyValue<SoundEvent> breakSoundIn, LazyValue<SoundEvent> stepSoundIn, LazyValue<SoundEvent> placeSoundIn, LazyValue<SoundEvent> hitSoundIn, LazyValue<SoundEvent> fallSoundIn) {
            // shut up java i dont care about the original constructor i just want this to be a sound type
            super(volumeIn, pitchIn, SoundEvents.BLOCK_STONE_BREAK, SoundEvents.BLOCK_STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
            // now we're talking
            this.breakSound = breakSoundIn;
            this.stepSound = stepSoundIn;
            this.placeSound = placeSoundIn;
            this.hitSound = hitSoundIn;
            this.fallSound = fallSoundIn;
        }

        public float getVolume() {
            return this.volume;
        }

        public float getPitch() {
            return this.pitch;
        }

        public SoundEvent getBreakSound() {
            return this.breakSound.getValue();
        }

        public SoundEvent getStepSound() {
            return this.stepSound.getValue();
        }

        public SoundEvent getPlaceSound() {
            return this.placeSound.getValue();
        }

        public SoundEvent getHitSound() {
            return this.hitSound.getValue();
        }

        public SoundEvent getFallSound() {
            return this.fallSound.getValue();
        }

    }
}
