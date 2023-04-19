package architectspalette.core.model;

import architectspalette.core.model.util.BakedModelWrapperWithData;
import architectspalette.core.model.util.QuadHelper;
import architectspalette.core.model.util.SpriteShift;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SheetMetalModel extends BakedModelWrapperWithData {

    //The model property used to store the board data.
    private static final ModelProperty<Data> CT_PROPERTY = new ModelProperty<>();

    private final SpriteShift spriteShift;

    public SheetMetalModel(BakedModel originalModel, SpriteShift spriteShift) {
        super(originalModel);
        this.spriteShift = spriteShift;
    }

    //Add model property to the model data builder.
    @Override
    protected ModelDataMap.Builder gatherModelData(ModelDataMap.Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state) {
        var data = new Data();
        initializeData(world, pos, state, data);
        return builder.withInitial(CT_PROPERTY, data);
    }

    private static void initializeData(BlockAndTintGetter world, BlockPos pos, BlockState state, Data data) {
        var checkPos = new BlockPos.MutableBlockPos();
        for (var face : Direction.values()) {
            if (Block.shouldRenderFace(state, world, pos, face, checkPos.setWithOffset(pos, face)) || state.getBlock() instanceof WallBlock) {
                int index = 1;
                boolean doShift = false;
                if (world.getBlockState(checkPos.setWithOffset(pos, getUpDirection(face))).is(state.getBlock())) {
                    index += 1;
                    doShift = true;
                }
                if (world.getBlockState(checkPos.setWithOffset(pos, getDownDirection(face))).is(state.getBlock())) {
                    index -= 1;
                    doShift = true;
                }
                if (doShift) {
                    data.set(face, index);
                }
            }
        }
    }

    private static Direction getUpDirection(Direction face) {
        return face.getAxis().isHorizontal() ? Direction.UP : Direction.NORTH;
    }
    private static Direction getDownDirection(Direction face) {
        return getUpDirection(face).getOpposite();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand, IModelData extraData) {
        List<BakedQuad> quads =  super.getQuads(state, side, rand);

        if (!extraData.hasProperty(CT_PROPERTY))
            return quads;

        Data data = extraData.getData(CT_PROPERTY);
        if (data == null) return quads;

        quads = new ArrayList<>(quads);


        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);
            var dir = quad.getDirection();
            var index = data.get(dir);
            if (index != -1) {
                BakedQuad newQuad = QuadHelper.clone(quad);
                int[] vertexData = newQuad.getVertices();
                float uShift = spriteShift.getUShift();
                float extraShift = (spriteShift.getVHeight() * index);
                float vShift = spriteShift.getVShift() + extraShift;

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

    private static class Data {

        private final int[] indices = new int[6];

        public Data() {
            Arrays.fill(indices, -1);
        }

        public void set(Direction face, int index) {
            indices[face.get3DDataValue()] = index;
        }

        public int get(Direction face) {
            return indices[face.get3DDataValue()];
        }

    }
}
