package architectspalette.core.model;

import architectspalette.core.model.util.BakedModelWrapperWithData;
import architectspalette.core.model.util.QuadHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TileModel extends BakedModelWrapperWithData {

    public TileModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    protected ModelData.Builder gatherModelData(ModelData.Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state) {
        return builder;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>(super.getQuads(state, side, rand, extraData, renderType));

        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);
            boolean shift = false;
            //Check for being horizontal.
            if (quad.getDirection().get3DDataValue() >= 2) {
                //Check for north/south
                if ((quad.getDirection().get3DDataValue() < 4)) {
                    shift = true;
                }
            }

            if (shift) {
                BakedQuad newQuad = QuadHelper.clone(quad);
                int[] vertexData = newQuad.getVertices();
                for (int vertex = 0; vertex < 4; vertex++) {
                    float u = QuadHelper.getU(quad.getVertices(), (vertex + 2) % 4);
//                    float v = QuadHelper.getV(quad.getVertices(), (vertex + 1) % 4);
                    QuadHelper.setU(vertexData, vertex, u);
//                    QuadHelper.setV(vertexData, vertex, v);
                }

                quads.set(i, newQuad);

            }
        }
        return quads;
    }
}
