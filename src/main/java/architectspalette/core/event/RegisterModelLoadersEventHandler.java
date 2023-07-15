package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.model.BoardModel;
import architectspalette.core.model.util.SpriteShift;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.ElementsModel;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterModelLoadersEventHandler {

    public static final String MODELTYPE_BOARDS = "boards";
    static {
        WrappedModelLoader.register(MODELTYPE_BOARDS, new ModelWrapper() {
            @Override
            public BakedModelWrapper<?> apply(IGeometryBakingContext context, BakedModel bakedModel, BlockModel blockModel) {
                ResourceLocation particle = blockModel.getMaterial("particle").texture();
                var odd = new ResourceLocation(particle.getNamespace(), particle.getPath() + "_odd");
                return new BoardModel(bakedModel, SpriteShift.getShift(particle, odd));
            }
        });
    }
    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register("wrapped", new WrappedModelLoader());
    }

    private static class WrappedModelLoader implements IGeometryLoader<WrappedUnbakedModel> {
        private static final ElementsModel.Loader LOADER = ElementsModel.Loader.INSTANCE;
        private static final Map<String, ModelWrapper> wrappers = new HashMap<>();

        private static void register(String name, ModelWrapper wrappingFunction) {
            wrappers.put(name, wrappingFunction);
        }

        @Override
        public WrappedUnbakedModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            var wrappedModel = jsonObject.get("wrapped_model").getAsJsonObject();
            var wrapperType = jsonObject.get("wrapper_type").getAsString();

            BlockModel baseModel = deserializationContext.deserialize(wrappedModel, BlockModel.class);

            // Wrap the model, giving the wrapping function assigned to the wrapper type.
            return new WrappedUnbakedModel(baseModel, wrappers.get(wrapperType));
        }
    }

    private static class WrappedUnbakedModel implements IUnbakedGeometry<WrappedUnbakedModel> {
        private final BlockModel wrappedModel;
        private final ModelWrapper wrapper;
        public WrappedUnbakedModel(BlockModel modelToWrap, ModelWrapper func) {
            this.wrappedModel = modelToWrap;
            this.wrapper = func;
        }

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            BakedModel baked = wrappedModel.bake(bakery, wrappedModel, spriteGetter, modelState, modelLocation, context.useBlockLight());
            return wrapper.apply(context, baked, wrappedModel);
        }

        @Override
        public Collection<Material> getMaterials(IGeometryBakingContext context, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return wrappedModel.getMaterials(modelGetter, missingTextureErrors);
        }
    }

    private abstract static class ModelWrapper {
        public abstract  BakedModelWrapper<?> apply(IGeometryBakingContext context, BakedModel bakedModel, BlockModel blockModel);
    }

}
