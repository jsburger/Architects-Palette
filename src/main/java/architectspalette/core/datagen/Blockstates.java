package architectspalette.core.datagen;

import architectspalette.content.blocks.BreadBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.util.BlockNode;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import static architectspalette.core.event.RegisterModelLoadersEventHandler.MODELTYPE_BOARDS;
import static architectspalette.core.registry.APBlocks.boards;
import static architectspalette.core.registry.util.BlockNode.BlockType.SLAB;
import static architectspalette.core.registry.util.BlockNode.BlockType.TILES;
import static architectspalette.core.registry.util.BlockNode.DataFlag.BOARDS;
import static architectspalette.core.registry.util.BlockNode.ExcludeFlag.MODELS;

public class Blockstates extends BlockStateProvider {
    private final BlockModelProvider awesomeBlockModels;
    private static BlockNode currentNode;

    private static final ResourceLocation dummy = new ResourceLocation("dummy:dummy");
    private static ResourceLocation currentModelName = dummy;

    public Blockstates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);

        this.awesomeBlockModels = new BlockModelProvider (gen, modid, exFileHelper) {
            @Override public void run(CachedOutput p_236071_) throws IOException {}
            @Override protected void registerModels() {}

            @Override
            //This seems like a bad idea, but it could be a really good idea.
            public BlockModelBuilder getBuilder(String path) {
                Preconditions.checkNotNull(path, "Path must not be null");
                ResourceLocation outputLoc = extendWithFolder(path.contains(":") ? new ResourceLocation(path) : new ResourceLocation(modid, path));
                this.existingFileHelper.trackGenerated(outputLoc, MODEL);
                var present = generatedModels.get(outputLoc);
                if (present == null) {
                    var builder = factory.apply(outputLoc);
                    generatedModels.put(outputLoc, builder);
                    var replacement = onNewModel(builder);
                    if (replacement != null) return replacement;
                    return builder;
                }
                return present;
            }

            @Override
            public BlockModelBuilder nested() {
                return factory.apply(currentModelName);
            }

            // weh weh weh lets make the function private so that jsburg has to copy paste it into his anonymous class
            // make THIS private �
            // thats the middle finger emoji
            private ResourceLocation extendWithFolder(ResourceLocation rl) {
                if (rl.getPath().contains("/")) {
                    return rl;
                }
                return new ResourceLocation(rl.getNamespace(), folder + "/" + rl.getPath());
            }
        };
    }

    @Override
    public BlockModelProvider models() {
        return awesomeBlockModels;
    }

    @Nullable
    private BlockModelBuilder onNewModel(BlockModelBuilder builder) {
        BlockModelBuilder returnvalue = null;
        if (currentNode != null) {
            currentModelName = builder.getUncheckedLocation();
            if (currentNode.getDataFlag(BOARDS)) {
                returnvalue = builder.customLoader(WrappedModelLoaderBuilder::new).setWrapper(MODELTYPE_BOARDS, models().nested());
            }

            currentModelName = dummy;
        }
        return returnvalue;
    }

    @Override
    protected void registerStatesAndModels() {

        for (BlockNode n : boards) {
            if (n.dataFlags != 0) {
                currentNode = n;
            }
            boardModel(n.getName(), n.get());
            currentNode = null;
        }

        cerebralTiles(APBlocks.CEREBRAL_BLOCK.getChild(TILES).get());

        BlockNode.forAllBaseNodes(this::processBlockNode);
        breadBlock(APBlocks.BREAD_BLOCK.get());
        breadSlab(APBlocks.BREAD_BLOCK.getChild(SLAB).get());

        itemModels().basicItem(APItems.ORACLE_JELLY.getId());
        itemModels().basicItem(APItems.CEREBRAL_PLATE.getId());


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

    private static String getBlockName(Block block) {
        return Registry.BLOCK.getKey(block).getPath();
    }

    private void verticalSlabBlock(VerticalSlabBlock block, ResourceLocation baseBlock) {
        //Model Generation
        ResourceLocation texture = inBlockFolder(baseBlock);
        ModelFile slab = models().withExistingParent(getBlockName(block), inAPBlockFolder("vertical_slab"))
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


    //Generates the funny item model for boards which loads the odd texture.
    private void boardModel(String boardname, Block board) {
        //Model file used by the block + blockstate
        simpleBlock(board);
        //Don't need to mess with nodes anymore
        currentNode = null;
        //Model File used by the item
        final ResourceLocation boardParent = inAPBlockFolder("boards/parent");
        ModelFile model = models().withExistingParent("block/boards/" + boardname + "_item", boardParent)
                .texture("even", inAPBlockFolder(boardname))
                .texture("odd", inAPBlockFolder(boardname + "_odd"));
        itemModels().getBuilder(boardname).parent(model);
    }


//    private void nubModel(String name, )

    private void processBlockNode(BlockNode node) {
        if (!node.getFlag(MODELS)) {

            Block block = node.get();
            String name = getBlockName(block);
            ResourceLocation parentTexture;
            if (node.parent != null) {
                parentTexture = inBlockFolder(node.parent.getId());
            } else {
                parentTexture = inBlockFolder(node.getId());
            }
            if (node.dataFlags != 0) {
                currentNode = node;
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
            if (currentNode != null) {
                currentNode = null;
            }
        }
        for (var child : node.children) {
            processBlockNode(child);
        }
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


    private void breadSlab(Block block) {
        var top = inAPBlockFolder("bread_block_inside");
        var sides = inAPBlockFolder("bread_block_side");

        ModelFile doubleModel = models().cubeColumn("double_bread_slab", sides, top);
        ModelFile upper = models().slabTop("bread_slab_top", sides, top, top);
        ModelFile lower = models().slab("bread_slab", sides, top, top);

        slabBlock((SlabBlock) block, lower, upper, doubleModel);
        simpleBlockItem(block, lower);
    }

    private void cerebralTiles(Block block) {
        ModelFile tex0 = models().cubeAll("cerebral_tiles", inAPBlockFolder("cerebral_tiles"));
        ModelFile tex1 = models().cubeAll("cerebral_tiles_1", inAPBlockFolder("cerebral_tiles_1"));
        ModelFile tex2 = models().cubeAll("cerebral_tiles_2", inAPBlockFolder("cerebral_tiles_2"));

        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(tex0), new ConfiguredModel(tex1), new ConfiguredModel(tex2));
        simpleBlockItem(block, tex0);
    }
//
//    //This class used to do a lot more but then I figured out how the model builder stuff worked
//    private static class AwesomeModelBuilder extends BlockModelBuilder {
//
//
//        public AwesomeModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
//            super(outputLocation, existingFileHelper);
//        }
//
//    }

    private static class WrappedModelLoaderBuilder <T extends ModelBuilder<T>> extends CustomLoaderBuilder<T> {

        private String wrapper_type;
        private BlockModelBuilder wrapped_model;
        public WrappedModelLoaderBuilder(T parent, ExistingFileHelper existingFileHelper) {
            super(ArchitectsPalette.rl("wrapped"), parent, existingFileHelper);
        }

        public BlockModelBuilder setWrapper(String type, BlockModelBuilder builder) {
            this.wrapper_type = type;
            this.wrapped_model = builder;
            return builder;
        }

        @Override
        public JsonObject toJson(JsonObject json) {
            var s = super.toJson(json);
            if (wrapper_type != null) {
                s.addProperty("wrapper_type", wrapper_type);
                s.add("wrapped_model", wrapped_model.toJson());
            }

            return s;
        }
    }

}
