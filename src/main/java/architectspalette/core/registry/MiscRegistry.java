package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MiscRegistry {

    // Tags
    public static final Tag.Named<Block> CRYSTAL_REPLACEABLE = BlockTags.bind(ArchitectsPalette.MOD_ID + ":" + "crystal_formation_replaceable");
    public static final Tag.Named<Block> GREEN_FIRE_SUPPORTING = BlockTags.bind(ArchitectsPalette.MOD_ID + ":" + "green_fire_supporting");

    //Particles
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ArchitectsPalette.MOD_ID);
    public static final RegistryObject<SimpleParticleType> GREEN_FLAME = PARTICLE_TYPES.register("green_flame", () -> new SimpleParticleType(false));

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        engine.register(GREEN_FLAME.get(), FlameParticle.Provider::new);
    }
}
