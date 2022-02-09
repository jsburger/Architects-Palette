package architectspalette.core.registry.util;

import architectspalette.common.blocks.VerticalSlabBlock;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.registries.RegistryObject;

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
        this(base_block, true);
    }

    public StoneBlockSet(RegistryObject<Block> base_block, Boolean auto_fill){
        this.block = base_block;
        this.material_name = getMaterialFromBlock(base_block.getId().getPath());
        if (auto_fill) {
            this.addAll();
        }
    }

    // Stone Bricks Slab -> Stone Brick Slab. Oak Planks Stairs -> Oak Stairs
    private String getMaterialFromBlock(String block) {
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

    public StoneBlockSet addSlabs() {
        slab = RegistryUtils.createBlock(material_name + "_slab", () -> new SlabBlock(properties()));
        verticalSlab = RegistryUtils.createBlock(material_name + "_vertical_slab", () -> new VerticalSlabBlock(properties()));
        return this;
    }

    public StoneBlockSet addStairs() {
        stairs = RegistryUtils.createBlock(material_name + "_stairs", () -> new StairBlock(() -> block.get().defaultBlockState(), properties()));
        return this;
    }

    public StoneBlockSet addWalls() {
        wall = RegistryUtils.createBlock(material_name + "_wall", () -> new WallBlock(properties()), CreativeModeTab.TAB_DECORATIONS);
        return this;
    }

    public StoneBlockSet addAll() {
        this.addSlabs();
        this.addStairs();
        this.addWalls();
        return this;
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

}
