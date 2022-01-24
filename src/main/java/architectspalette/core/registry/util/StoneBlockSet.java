package architectspalette.core.registry.util;

import architectspalette.common.blocks.VerticalSlabBlock;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.registries.RegistryObject;

public class StoneBlockSet {
    public RegistryObject<Block> SLAB;
    public RegistryObject<Block> VERTICAL_SLAB;
    public RegistryObject<Block> STAIRS;
    public RegistryObject<Block> WALL;
    public RegistryObject<Block> BLOCK;
    private final String material_name;

    public StoneBlockSet(RegistryObject<Block> base_block) {
        this(base_block, true);
    }

    public StoneBlockSet(RegistryObject<Block> base_block, Boolean auto_fill){
        this.BLOCK = base_block;
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
        return Block.Properties.copy(BLOCK.get());
    }

    public Block get() {
        return this.BLOCK.get();
    }

    public StoneBlockSet addSlabs() {
        SLAB = RegistryUtils.createBlock(material_name + "_slab", () -> new SlabBlock(properties()));
        VERTICAL_SLAB = RegistryUtils.createBlock(material_name + "_vertical_slab", () -> new VerticalSlabBlock(properties()));
        return this;
    }

    public StoneBlockSet addStairs() {
        STAIRS = RegistryUtils.createBlock(material_name + "_stairs", () -> new StairBlock(() -> BLOCK.get().defaultBlockState(), properties()));
        return this;
    }

    public StoneBlockSet addWalls() {
        WALL = RegistryUtils.createBlock(material_name + "_wall", () -> new WallBlock(properties()), CreativeModeTab.TAB_DECORATIONS);
        return this;
    }

    public StoneBlockSet addAll() {
        this.addSlabs();
        this.addStairs();
        this.addWalls();
        return this;
    }


}
