package architectspalette.core.datagen;

import architectspalette.content.blocks.VerticalSlabBlock;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.stream.Stream;

public class Blockstates extends BlockStateProvider {
    public Blockstates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        Stream<StoneBlockSet> boards = Stream.of(APBlocks.BIRCH_BOARDS, APBlocks.SPRUCE_BOARDS);
        boards.forEach(this::processBoardBlockSet);



    }


    private static ResourceLocation inBlockFolder(ResourceLocation original) {
        return new ResourceLocation(original.getNamespace(), "block/" + original.getPath());
    }

    private static ResourceLocation inAPBlockFolder(String name) {
        return modResourceLocation("block/" + name);
    }

    private static ResourceLocation modResourceLocation(String name) {
        return new ResourceLocation(ArchitectsPalette.MOD_ID, name);
    }

    private int getYRotation(Direction direction) {
        return switch(direction) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }

    private void verticalSlabBlock(VerticalSlabBlock block, ResourceLocation baseBlock) {
        ResourceLocation texture = inBlockFolder(baseBlock);
        ModelFile slab = models().withExistingParent(block.getRegistryName().getPath(), inAPBlockFolder("vertical_slab"))
                .texture("bottom", texture)
                .texture("top", texture)
                .texture("side", texture);
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    VerticalSlabBlock.VerticalSlabType type = state.getValue(VerticalSlabBlock.TYPE);
                    if (type != VerticalSlabBlock.VerticalSlabType.DOUBLE) {
                        return ConfiguredModel.builder()
                                .modelFile(slab)
                                .rotationY(getYRotation(type.direction))
                                .uvLock(true)
                                .build();
                    }
                    else {
                        return ConfiguredModel.builder()
                                .modelFile(models().getExistingFile(baseBlock))
                                .build();
                    }
                }, BlockStateProperties.WATERLOGGED);
        simpleBlockItem(block, slab);
    }


    private void processBoardBlockSet(StoneBlockSet set) {
        ResourceLocation base = set.getRegistryPart(StoneBlockSet.SetComponent.BLOCK).getId();
        set.forEachPart((part, block) -> {
            String name = Objects.requireNonNull(block.getRegistryName()).getPath();
            switch (part) {
                case BLOCK -> boardModel(base.getPath(), set.get());
                case SLAB -> {
                    slabBlock((SlabBlock) block, base, inBlockFolder(base));
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(inAPBlockFolder(name)));
                }
                case VERTICAL_SLAB -> verticalSlabBlock((VerticalSlabBlock) block, base);
                case STAIRS -> {
                    stairsBlock((StairBlock) block, inBlockFolder(base));
                    simpleBlockItem(block, new ModelFile.UncheckedModelFile(inAPBlockFolder(name)));
                }
            }

        });
    }

    //Generates the funny item model for boards which loads the odd texture.
    private void boardModel(String boardname, Block board) {
        //Model File used by the item
        final ResourceLocation boardParent = inAPBlockFolder("boards/parent");
        ModelFile model = models().withExistingParent("block/boards/" + boardname + "_item", boardParent)
                .texture("even", inAPBlockFolder(boardname))
                .texture("odd", inAPBlockFolder(boardname + "_odd"));
        itemModels().getBuilder(boardname).parent(model);
        //Model file used by the block + blockstate
        simpleBlock(board);
    }

    private void processBlockNode(BlockNode node) {

    }


}
