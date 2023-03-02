package architectspalette.core.registry.util;

import architectspalette.content.blocks.NubBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class BlockNode {

    private final BlockNode parent;
    private ArrayList<BlockNode> children;
    private final RegistryObject<Block> block;

    private final Style style;
    private final BlockType type;
    private final Tool tool;

    protected BlockNode(BlockNode parent, RegistryObject<Block> block, BlockType type, Tool tool, Style style) {
        this.parent = parent;
        this.block = block;
        this.type = type;
        this.tool = tool;
        this.style = style;
    }
    private void setChildren(ArrayList<BlockNode> children) {
        this.children = children;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    /**
     * Abyssaline.get(BRICKS, SLAB) -> Abyssaline Brick Slab
     */
    public RegistryObject<Block> get(BlockType... types) {
        for (BlockNode node : children) {
            if (node.type == types[0]) {
                //If length is one, then this is the last "part" to get.
                if (types.length == 1) {
                    return node.getBlock();
                }
                //Otherwise, pop the first part off and check those in the child.
                return node.get(Arrays.copyOfRange(types, 1, types.length));
            }
        }
        return null;
    }

    public ResourceLocation getId() {
        return block.getId();
    }
    public String getName() {
        return getId().getPath();
    }

    public void ForEach(Consumer<BlockNode> consumer) {
        consumer.accept(this);
        for (BlockNode node : children) {
            node.ForEach(consumer);
        }
    }


    public static class Builder {
        protected Builder parent;
        private final ArrayList<Builder> children = new ArrayList<>();
        private RegistryObject<Block> block;

        private Style style;
        private final BlockType type;
        private Tool tool;
        private ArrayList<DataFlags> flags = new ArrayList<>();
        private String name;
        //TODO: Tags (?)

        protected Builder(Builder parent, BlockType type) {
            this.parent = parent;
            this.type = type;

        }
        public Builder() {
            this(null, BlockType.BASE);
        }
        public Builder(BlockType type) {
            this(null, type);
        }

        private void inherit() {
            //TODO: Flags & Tags
            if (parent != null) {
                this.style = style == null ? parent.style : style;
                this.tool = tool == null ? parent.tool : tool;
            }
        }

        //Public version won't let you build child builders.
        public BlockNode build() {
            if (parent == null) {
                return build(null);
            }
            throw new IllegalStateException("#build() was called on a child builder. Don't do that.");
        }
        private BlockNode build(BlockNode parent) {
            inherit();

            if (block == null) {
                name = modifyBlockNameForType(type, this.parent.name);
                block = makeBlock();
            }
            BlockNode built = new BlockNode(parent, block, type, tool, style);
            ArrayList<BlockNode> nodeChildren = new ArrayList<>();
            for (Builder builder : children) {
                nodeChildren.add(builder.build(built));
            }
            built.setChildren(nodeChildren);
            return built;
        }

        private Builder addChild(BlockType type) {
            Builder child = new Builder(this, type);
            children.add(child);
            return child;
        }

        public Builder addPart(BlockType type, Consumer<Builder> builderCode) {
            Builder child = addChild(type);
            builderCode.accept(child);
            return this;
        }
        public Builder addPart(BlockType type) {
            addChild(type);
            return this;
        }
        public Builder variants(BlockType... types) {
            for (BlockType type : types) {
                addChild(type);
            }
            return this;
        }

        public Builder base(RegistryObject<Block> block) {
            this.block = block;
            if (this.name == null) this.name = block.getId().getPath();
            return this;
        }

        public Builder tool(Tool tool) {
            this.tool = tool;
            return this;
        }
        public Builder style(Style style) {
            this.style = style;
            return this;
        }
        public Builder flag(DataFlags flag) {
            flags.add(flag);
            return this;
        }


        public Builder slabs() {
            addChild(BlockType.SLAB);
            addChild(BlockType.VERTICAL_SLAB);
            return this;
        }

        public Builder bricks(Consumer<Builder> brickCode) {
            Builder bricks = addChild(BlockType.BRICKS);
            bricks.style = Style.CUBE;
            brickCode.accept(bricks);
            return this;
        }
        public Builder tiles(Consumer<Builder> tileCode) {
            Builder bricks = addChild(BlockType.TILES);
            bricks.style = Style.CUBE;
            tileCode.accept(bricks);
            return this;
        }

        private Builder getBaseParent() {
            Builder p = parent;
            if (parent == null) {
                return this;
            }
            return p.getBaseParent();
        }

        private BlockBehaviour.Properties getProperties() {
            //TODO: Add property modifiers (Property Consumer)
            return BlockBehaviour.Properties.copy(parent.block.get());
        }

        private RegistryObject<Block> makeBlock() {
            return RegistryUtils.createBlock(name, () -> {
                Block block = parent.block.get();
                if (block instanceof IBlockSetBase base) {
                    return base.getBlockForType(type, getProperties(), block);
                }
                return getBlockForType(type, getProperties(), block);
            }, getTabForType(type));
        }

    }

    public enum Style {
        CUBE,
        TOP_SIDES,
        TOP_SIDE_BOTTOM
    }

    public enum BlockType {
        BASE,
        BRICKS,
        CRACKED,
        MOSSY,
        TILES,
        CHISELED,
        PILLAR,
        NUB,
        SLAB,
        VERTICAL_SLAB,
        STAIRS,
        WALL,
        FENCE,
        LAMP,
        PLATING
    }

    public enum Tool {
        PICK(BlockTags.MINEABLE_WITH_PICKAXE, null),
        STONE_PICK(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL),
        IRON_PICK(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL),
        DIAMOND_PICK(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL),

        AXE(BlockTags.MINEABLE_WITH_AXE, null),
        HOE(BlockTags.MINEABLE_WITH_HOE, null),
        SHOVEL(BlockTags.MINEABLE_WITH_SHOVEL, null);


        final TagKey<Block> toolTag;
        final TagKey<Block> miningTag;
        Tool(TagKey<Block> toolTag, TagKey<Block> miningTag) {
            this.toolTag = toolTag;
            this.miningTag = miningTag;
        }
        public TagKey<Block> getToolTag() {
            return toolTag;
        }
        public TagKey<Block> getMiningTag() {
            return miningTag;
        }
    }

    public enum DataFlags {
        ABYSSALINE
    }

    public static Block getBlockForType(BlockType part, BlockBehaviour.Properties properties, Block base) {
        return switch (part) {
            case WALL -> new WallBlock(properties);
            case SLAB -> new SlabBlock(properties);
            case VERTICAL_SLAB -> new VerticalSlabBlock(properties);
            case STAIRS -> new StairBlock(base::defaultBlockState, properties);
            case FENCE -> new FenceBlock(properties);
            case PILLAR -> new RotatedPillarBlock(properties);
            case NUB -> new NubBlock(properties);
            case BASE -> throw new IllegalStateException("Should not call makeBlock on BASE");
            //erm.... idk if this works LOL
            default -> {
                try {
                    yield base.getClass().getConstructor().newInstance(properties);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private static String modifyBlockNameForType(BlockType type, String baseName) {
        var material = getMaterialFromBlock(baseName);
        return switch (type) {

            case BASE -> baseName;
            case BRICKS -> material + "_bricks";
            case CRACKED -> "cracked_" + baseName;
            case MOSSY -> "mossy_" + baseName;
            case TILES -> material + "_tiles";
            case CHISELED -> "chiseled_" + baseName;
            case PILLAR -> getMaterialAggressive(baseName) + "_pillar";
            case NUB -> material + "_nub";
            case SLAB -> material + "_slab";
            case VERTICAL_SLAB -> material + "_vertical_slab";
            case STAIRS -> material + "_stairs";
            case WALL -> material + "_wall";
            case FENCE -> material + "_fence";
            case LAMP -> material + "_lamp";
            case PLATING -> material + "_plating";
        };
    }

    private static CreativeModeTab getTabForType(BlockType type) {
        return switch(type) {
            case NUB, WALL, FENCE -> CreativeModeTab.TAB_DECORATIONS;
            default -> CreativeModeTab.TAB_BUILDING_BLOCKS;
        };
    }

    private static String getMaterialFromBlock(String block) {
        return block
                .replace("bricks", "brick")
                .replace("_planks", "")
                .replace("_block", "")
                .replace("tiles", "tile")
                .replace("boards", "board");
    }

    private static String getMaterialAggressive(String block) {
        return getMaterialFromBlock(block)
                .replace("_brick", "")
                .replace("_tile", "")
                .replace("chiseled_", "");
    }

}
