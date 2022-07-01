package architectspalette.core.registry.util;

import architectspalette.content.blocks.VerticalSlabBlock;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class StoneBlockSet {
    public RegistryObject<Block> slab;
    public RegistryObject<Block> verticalSlab;
    public RegistryObject<Block> stairs;
    public RegistryObject<Block> wall;
    public RegistryObject<Block> block;
    private final String material_name;

    public StoneBlockSet(RegistryObject<Block> base_block) {
        this(base_block, SetComponent.ALL);
    }

    public StoneBlockSet(RegistryObject<Block> base_block, SetComponent... parts){
        this.block = base_block;
        this.material_name = getMaterialFromBlock(base_block.getId().getPath());
        Arrays.stream(parts).forEachOrdered(this::addPart);
    }

    // Stone Bricks Slab -> Stone Brick Slab. Oak Planks Stairs -> Oak Stairs
    private static String getMaterialFromBlock(String block) {
        return block
                .replace("bricks", "brick")
                .replace("_planks", "")
                .replace("_block", "")
                .replace("tiles", "tile");
    }

    private Block.Properties properties() {
        return Block.Properties.copy(block.get());
    }

    public Block get() {
        return this.block.get();
    }

    private void addPart(SetComponent part) {
        switch (part) {
            case ALL -> addAll();
            case WALL -> addWalls();
            case SLABS -> addSlabs();
            case STAIRS -> addStairs();
            case NO_WALLS -> {addSlabs(); addStairs();}
            case NO_STAIRS -> {addSlabs(); addWalls();}
        }
    }

    public void addSlabs() {
        slab = RegistryUtils.createBlock(material_name + "_slab", () -> new SlabBlock(properties()));
        verticalSlab = RegistryUtils.createBlock(material_name + "_vertical_slab", () -> new VerticalSlabBlock(properties()));
    }

    public void addStairs() {
        stairs = RegistryUtils.createBlock(material_name + "_stairs", () -> new StairBlock(() -> block.get().defaultBlockState(), properties()));
    }

    public void addWalls() {
        wall = RegistryUtils.createBlock(material_name + "_wall", () -> new WallBlock(properties()), CreativeModeTab.TAB_DECORATIONS);
    }

    public void addAll() {
        this.addSlabs();
        this.addStairs();
        this.addWalls();
    }



    private Stream<Block> blockStream() {
        //Puts all blocks in a Stream, filters out null entries, then gets the blocks from their registry object
        return Stream.of(slab, verticalSlab, stairs, wall, block).filter(Objects::nonNull).map(RegistryObject::get);
    }

    public void forEach(Consumer<? super Block> action) {
        this.blockStream().forEach(action);
    }

    public void registerFlammable(int encouragement, int flammability) {
        this.forEach((b) -> DataUtils.registerFlammable(b, encouragement, flammability));
    }

    public enum SetComponent {
        SLABS,
        STAIRS,
        WALL,
        ALL,
        NO_WALLS,
        NO_STAIRS;
    }

}
