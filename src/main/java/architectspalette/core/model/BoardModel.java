package architectspalette.core.model;

import architectspalette.core.model.util.BakedModelWrapperWithData;
import architectspalette.core.model.util.QuadHelper;
import architectspalette.core.model.util.SpriteShift;
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
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BoardModel extends BakedModelWrapperWithData {

    //The model property used to store the board data.
    private static final ModelProperty<BoardData> BOARD_PROPERTY = new ModelProperty<>();

    private final SpriteShift spriteShift;

    public BoardModel(BakedModel originalModel, SpriteShift boardShift) {
        super(originalModel);
        this.spriteShift = boardShift;
    }

    //Add model property to the model data builder.
    @Override
    protected ModelData.Builder gatherModelData(ModelData.Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state) {
        return builder.with(BOARD_PROPERTY, new BoardData(pos));
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
        List<BakedQuad> quads =  super.getQuads(state, side, rand, extraData, renderType);

        if (!extraData.has(BOARD_PROPERTY))
            return quads;

        BoardData data = extraData.get(BOARD_PROPERTY);
        if (data == null) return quads;

        quads = new ArrayList<>(quads);

        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);
            boolean shift;
            //Check for being horizontal.
            if (quad.getDirection().get3DDataValue() >= 2) {
                //If the horizontal faces should be shifted
                shift = data.getHorizontal();
                //Check for north/south
                if ((quad.getDirection().get3DDataValue() < 4)) {
                    shift = !shift;
                }
            }
            //Vertical face.
            else {
                //If the vertical faces should be shifted
                shift = data.getVertical();
                if (quad.getDirection().get3DDataValue() == 0) {
                    shift = !shift;
                }
            }

            if (shift) {
                BakedQuad newQuad = QuadHelper.clone(quad);
                int[] vertexData = newQuad.getVertices();
                float uShift = spriteShift.getUShift();
                float vShift = spriteShift.getVShift();

                for (int vertex = 0; vertex < 4; vertex++) {
                    float u = QuadHelper.getU(vertexData, vertex);
                    float v = QuadHelper.getV(vertexData, vertex);
                    QuadHelper.setU(vertexData, vertex, u + uShift);
                    QuadHelper.setV(vertexData, vertex, v + vShift);
                }

                quads.set(i, newQuad);
            }

        }

        return quads;
    }

    private static class BoardData {
        private final boolean isXOdd;
        private final boolean isZOdd;
        public BoardData(BlockPos pos) {
            this(isOdd(pos.getX()), isOdd(pos.getZ()));
        }
        public BoardData(boolean isOddX, boolean isOddZ) {
            this.isXOdd = isOddX;
            this.isZOdd = isOddZ;
        }
        public boolean getHorizontal() {
            return (isZOdd ^ isXOdd);
        }
        public boolean getVertical() {
            return isXOdd;
        }
        private static boolean isOdd(int num) {
            return (Math.abs(num) % 2) == 1;
        }
    }
}
