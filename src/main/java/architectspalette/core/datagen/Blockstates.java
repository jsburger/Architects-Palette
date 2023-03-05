package architectspalette.core.datagen;

import architectspalette.content.blocks.BreadBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.stream.Stream;

import static architectspalette.core.registry.util.BlockNode.ExcludeFlag.MODELS;

public class Blockstates extends BlockStateProvider {
    public Blockstates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        Stream<StoneBlockSet> boards = Stream.of(APBlocks.BIRCH_BOARDS, APBlocks.SPRUCE_BOARDS);
        boards.forEach(this::processBoardBlockSet);

        BlockNode.forAllBaseNodes(this::processBlockNode);
        breadBlock(APBlocks.BREAD_BLOCK.getBlock());
//        processBlockNode(APBlocks.TREAD_PLATE);
//        processBlockNode(APBlocks.HAZARD_BLOCK);

    }


    private static ResourceLocation inBlockFolder(ResourceLocation original) {
        return new ResourceLocation(original.getNamespace(), "block/" + original.getPath());
    }
    private static ResourceLocation inBlockFolder(ResourceLocation original, String append) {
        return new ResourceLocation(original.getNamespace(), "block/" + original.getPath() + append);
    }

    private static ResourceLocation inMinecraftBlock(String name) {
        return new ResourceLocation("minecraft", "block/" + name);
    }

    private static String fileName(ResourceLocation block) {
        return "block/" + block.getPath();
    }

    private static ResourceLocation inAPBlockFolder(String name) {
        return modResourceLocation("block/" + name);
    }

    private static ResourceLocation modResourceLocation(String name) {
        return new ResourceLocation(ArchitectsPalette.MOD_ID, name);
    }

    private static int getYRotation(Direction direction) {
        return switch(direction) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }

    private static ModelFile blockModel(String name) {
        return new ModelFile.UncheckedModelFile(inAPBlockFolder(name));
    }

    private void simpleBlockstate(Block block, ModelFile model) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(model));
    }

    private void verticalSlabBlock(VerticalSlabBlock block, ResourceLocation baseBlock) {
        //Model Generation
        ResourceLocation texture = inBlockFolder(baseBlock);
        ModelFile slab = models().withExistingParent(block.getRegistryName().getPath(), inAPBlockFolder("vertical_slab"))
                .texture("bottom", texture)
                .texture("top", texture)
                .texture("side", texture);
        //Blockstate Generation
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
                    simpleBlockItem(block, blockModel(name));
                }
                case VERTICAL_SLAB -> verticalSlabBlock((VerticalSlabBlock) block, base);
                case STAIRS -> {
                    stairsBlock((StairBlock) block, inBlockFolder(base));
                    simpleBlockItem(block, blockModel(name));
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

    //Lotta textures here. How about that.
    private void breadBlock(Block block) {
        var heel = inAPBlockFolder("bread_block_end");
        var inside = inAPBlockFolder("bread_block_inside");
        var whole_bottom = inAPBlockFolder("bread_block_bottom");
        var middle_bottom = inAPBlockFolder("bread_block_bottom_middle");
        var left_bottom = inAPBlockFolder("bread_block_bottom_left");
        var right_bottom = inAPBlockFolder("bread_block_bottom_right");
        var whole_top = inAPBlockFolder("bread_block_top");
        var middle_top = inAPBlockFolder("bread_block_top_middle");
        var left_top = inAPBlockFolder("bread_block_top_left");
        var right_top = inAPBlockFolder("bread_block_top_right");
        var whole_side = inAPBlockFolder("bread_block_side");
        var middle_side = inAPBlockFolder("bread_block_side_middle");
        var left_side = inAPBlockFolder("bread_block_side_left");
        var right_side = inAPBlockFolder("bread_block_side_right");

        ModelFile wholeModel = models().cube("block/bread_block_whole", whole_bottom, whole_top, whole_side, whole_side, whole_side, whole_side).texture("particle", inside);
        ModelFile rightModel = models().cube("block/bread_block_right", right_bottom, right_top, heel, inside, right_side, left_side).texture("particle", inside);
        ModelFile leftModel = models().cube("block/bread_block_left", left_bottom, left_top, inside, heel, left_side, right_side).texture("particle", inside);
        ModelFile middleModel = models().cube("block/bread_block_middle", middle_bottom, middle_top, inside, inside, middle_side, middle_side).texture("particle", inside);

        getVariantBuilder(block)
                .forAllStates(state -> {
                    var part = state.getValue(BreadBlock.PART);
                    var axis = state.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                    if (part == BreadBlock.BreadPart.WHOLE) {
                        return ConfiguredModel.builder().modelFile(wholeModel).build();
                    }
                    var yRotation = axis == Direction.Axis.X ? 270 : 0;
                    var model = part == BreadBlock.BreadPart.LEFT ? leftModel : part == BreadBlock.BreadPart.RIGHT ? rightModel : middleModel;
                    return ConfiguredModel.builder().modelFile(model).rotationY(yRotation).build();

                });

        itemModels().getBuilder("bread_block").parent(rightModel);
    }

//    private void nubModel(String name, )

    private void processBlockNode(BlockNode node) {
        if (!node.getFlag(MODELS)) {
            //Set up textures
            ResourceLocation top, mid, bot;
            switch (node.style) {
                default -> {
                    top = inBlockFolder(node.getId());
                    mid = inBlockFolder(node.getId());
                    bot = inBlockFolder(node.getId());
                }
                case TOP_SIDES -> {
                    top = inBlockFolder(node.getId(), "_top");
                    mid = inBlockFolder(node.getId());
                    bot = inBlockFolder(node.getId(), "_top");
                }
                case TOP_SIDE_BOTTOM -> {
                    top = inBlockFolder(node.getId(), "_top");
                    mid = inBlockFolder(node.getId());
                    bot = inBlockFolder(node.getId(), "_bottom");
                }
            }
            ;

            Block block = node.getBlock();
            String name = block.getRegistryName().getPath();
            ResourceLocation parentTexture;
            if (node.parent != null) {
                parentTexture = inBlockFolder(node.parent.getId());
            } else {
                parentTexture = inBlockFolder(node.getId());
            }
            //TODO: Abyssaline support, will require a complete rewrite :)
            switch (node.type) {
                case NUB -> {
                    //TODO: Nub Models
                    //                nubModel()
                }
                case SLAB -> {
                    //TODO: Fancy Slabs
                    slabBlock((SlabBlock) block, node.parent.getId(), parentTexture);
                    simpleBlockItem(block, blockModel(name));
                }
                case VERTICAL_SLAB -> {
                    verticalSlabBlock((VerticalSlabBlock) block, node.parent.getId());
                }
                case STAIRS -> {
                    stairsBlock((StairBlock) block, parentTexture);
                    simpleBlockItem(block, blockModel(name));
                }
                case WALL -> {
                    //TODO: Fancy walls
                    wallBlock((WallBlock) block, parentTexture);
                    var inventory = models().withExistingParent(fileName(node.getId()), inMinecraftBlock("wall_inventory"))
                            .texture("wall", parentTexture);
                    simpleBlockItem(block, inventory);
                }
                case FENCE -> {
                    //TODO: Fancy fences
                    fenceBlock((FenceBlock) block, parentTexture);
                    simpleBlockItem(block, blockModel(name));
                }
                case PILLAR -> {
                    //TODO: 6 way pillars (Flint, Hadaline)
                    logBlock((RotatedPillarBlock) block);
                    simpleBlockItem(block, blockModel(name));
                }
                //All Cubes are handled the same way.
                default -> {

                    //Create basic model
                    String filename = fileName(node.getId());
                    ModelFile model = switch (node.style) {
                        case CUBE -> models().withExistingParent(filename, inMinecraftBlock("cube_all"))
                                .texture("all", mid);
                        case TOP_SIDES -> models().withExistingParent(filename, inMinecraftBlock("cube_column"))
                                .texture("end", top)
                                .texture("side", mid);
                        case TOP_SIDE_BOTTOM -> models().withExistingParent(filename, inMinecraftBlock("cube_bottom_top"))
                                .texture("top", top)
                                .texture("side", mid)
                                .texture("bottom", bot);
                    };

                    simpleBlockstate(block, model);
                    simpleBlockItem(block, model);

                }
            }
        }
        for (var child : node.getChildren()) {
            processBlockNode(child);
        }
    }


}
