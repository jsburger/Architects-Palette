package architectspalette.core.registry.util;

import architectspalette.content.blocks.NubBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static architectspalette.core.registry.util.StoneBlockSet.SetComponent.*;

public class StoneBlockSet {
    private final String material_name;
    private final List<RegistryObject<? extends Block>> parts;

    public StoneBlockSet(RegistryObject<? extends Block> base_block) {
        this(base_block, SetGroup.TYPICAL);
    }

    public StoneBlockSet(RegistryObject<? extends Block> base_block, SetGroup group) {
        this(base_block, group.components);
    }

    public StoneBlockSet(RegistryObject<? extends Block> base_block, SetGroup group, SetComponent... additionalParts) {
        this(base_block, concatArray(group.components, additionalParts));
    }

    public StoneBlockSet(RegistryObject<? extends Block> base_block, SetComponent... parts){
        this.parts = new ArrayList<>();
        //Piece of crap array list doesn't let me preallocate indices ((if it can, you should let me know))
        for (int i = 0; i < values().length; i++) {
            this.parts.add(null);
        }
        this.material_name = getMaterialFromBlock(base_block.getId().getPath());
        setPart(BLOCK, base_block);
        Arrays.stream(parts).forEachOrdered(this::createPart);
    }

    // Stone Bricks Slab -> Stone Brick Slab. Oak Planks Stairs -> Oak Stairs
    private static String getMaterialFromBlock(String block) {
        return block
                .replace("bricks", "brick")
                .replace("_planks", "")
                .replace("_block", "")
                .replace("tiles", "tile");
    }

    // Go all the way. Stone Bricks -> Stone. Meant for pillars and such.
    // Written with the assumption that nobody will ever want a pillar and a brick pillar.
    private static String getMaterialAggressive(String block) {
        return getMaterialFromBlock(block)
                .replace("_brick", "")
                .replace("_tile", "")
                .replace("chiseled_", "");
    }

    private Block.Properties properties() {
        return Block.Properties.copy(getPart(BLOCK));
    }

    //Convenience function so it matches RegistryObjects
    public Block get() {
        return getPart(BLOCK);
    }

    private Stream<? extends Block> blockStream() {
        //Puts all blocks in a Stream, filters out null entries, then gets the blocks from their registry object
        return parts.stream().filter(Objects::nonNull).map(RegistryObject::get);
    }

    public void forEach(Consumer<? super Block> action) {
        this.blockStream().forEach(action);
    }

    public void registerFlammable(int encouragement, int flammability) {
        this.forEach((b) -> DataUtils.registerFlammable(b, encouragement, flammability));
    }

    public enum SetComponent {
        BLOCK("", CreativeModeTab.TAB_BUILDING_BLOCKS),
        SLAB("_slab", CreativeModeTab.TAB_BUILDING_BLOCKS),
        VERTICAL_SLAB("_vertical_slab", CreativeModeTab.TAB_BUILDING_BLOCKS),
        STAIRS("_stairs", CreativeModeTab.TAB_BUILDING_BLOCKS),
        WALL("_wall", CreativeModeTab.TAB_DECORATIONS),
        FENCE("_fence", CreativeModeTab.TAB_DECORATIONS),
        PILLAR(SetComponent::pillarName, CreativeModeTab.TAB_BUILDING_BLOCKS),
        NUB("_nub", CreativeModeTab.TAB_DECORATIONS);

        public final CreativeModeTab tab;
        public final Function<String, String> nameGenerator;
        SetComponent(String suffix, CreativeModeTab tab) {
            this((material) -> addSuffix(material, suffix), tab);
        }
        SetComponent(Function<String, String> nameGen, CreativeModeTab tab) {
            this.nameGenerator = nameGen;
            this.tab = tab;
        }
        public String getName(String material) {
            return nameGenerator.apply(material);
        }

        private static String addSuffix(String material, String suffix) {
            return material + suffix;
        }
        private static String pillarName(String material) {
            return getMaterialAggressive(material) + "_pillar";
        }
    }

    public enum SetGroup {
        SLABS(SLAB, VERTICAL_SLAB),
        NO_WALLS(SLAB, VERTICAL_SLAB, STAIRS),
        NO_STAIRS(SLAB, VERTICAL_SLAB, WALL),
        TYPICAL(SLAB, VERTICAL_SLAB, STAIRS, WALL);

        public final SetComponent[] components;
        SetGroup(SetComponent... components) {
            this.components = components;
        }
        public void forEach(Consumer<SetComponent> action) {
            Arrays.stream(components).forEachOrdered(action);
        }
    }

    public Block getPart(SetComponent part) {
        return parts.get(part.ordinal()).get();
    }
    private void setPart(SetComponent part, RegistryObject<? extends Block> block) {
        parts.add(part.ordinal(), block);
    }
    private void createPart(SetComponent part) {
        setPart(part, makePart(part));
    }

    private RegistryObject<Block> makePart(SetComponent part) {
        return RegistryUtils.createBlock(part.getName(material_name), () -> {
            Block block = getPart(BLOCK);
            if (block instanceof IBlockSetBase base) {
                return base.getBlockForPart(part, properties(), block);
            }
            return getBlockForPart(part, properties(), block);
        }, part.tab);
    }

    public static Block getBlockForPart(SetComponent part, BlockBehaviour.Properties properties, Block base) {
        return switch (part) {
            case WALL -> new WallBlock(properties);
            case SLAB -> new SlabBlock(properties);
            case VERTICAL_SLAB -> new VerticalSlabBlock(properties);
            case STAIRS -> new StairBlock(base::defaultBlockState, properties);
            case FENCE -> new FenceBlock(properties);
            case PILLAR -> new RotatedPillarBlock(properties);
            case NUB -> new NubBlock(properties);
            case BLOCK -> throw new IllegalStateException("Should not call makePart on BLOCK. Use setPart instead.");
        };
    }

    private static <T> T[] concatArray(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

}
