package architectspalette.core.registry.util;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraftforge.fml.RegistryObject;

public class StoneBlockSet {
    public RegistryObject<Block> SLAB;
    public RegistryObject<Block> STAIRS;
    public RegistryObject<Block> WALL;
    public RegistryObject<Block> BLOCK;
    private final String material_name;

    public StoneBlockSet(RegistryObject<Block> base_block){
        this.BLOCK = base_block;
        this.material_name = getMaterialFromBlock(base_block.getId().getPath());
    }

    // Stone Bricks Slab -> Stone Brick Slab. Oak Planks Stairs -> Oak Stairs
    private String getMaterialFromBlock(String block) {
        return block
                .replace("bricks", "brick")
                .replace("_planks", "")
                .replace("tiles", "tile");
    }

    private Block.Properties properties() {
        return Block.Properties.from(BLOCK.get());
    }

    public Block get() {
        return this.BLOCK.get();
    }

    public StoneBlockSet addSlabs() {
        SLAB = RegistryUtils.createBlock(material_name + "_slab", () -> new SlabBlock(properties()));
        return this;
    }

    public StoneBlockSet addStairs() {
        STAIRS = RegistryUtils.createBlock(material_name + "_stairs", () -> new StairsBlock(() -> BLOCK.get().getDefaultState(), properties()));
        return this;
    }

    public StoneBlockSet addWalls() {
        WALL = RegistryUtils.createBlock(material_name + "_wall", () -> new WallBlock(properties()));
        return this;
    }

    public StoneBlockSet addAll() {
        this.addSlabs();
        this.addStairs();
        this.addWalls();
        return this;
    }


}
