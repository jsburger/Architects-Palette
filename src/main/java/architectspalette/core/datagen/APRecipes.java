package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.registry.util.BlockNode;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.ConditionalRecipe;

import java.util.ArrayList;
import java.util.function.Consumer;

public class APRecipes extends RecipeProvider {
    public APRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        BlockNode.forAllBaseNodes((node) -> processBlockNode(consumer, node));
//        processBlockNode(consumer, APBlocks.TREAD_PLATE);
//        processBlockNode(consumer, APBlocks.HAZARD_BLOCK);
    }

    private static void processBlockNode(Consumer<FinishedRecipe> consumer, BlockNode node) {
        String hasBase = "has_" + node.getName();
        node.forEach((n -> {
            if (n.parent != null && !n.getFlag(BlockNode.ExcludeFlag.RECIPES)) {
                Block block = n.get();
                Block parent = n.parent.get();

                int stoneCuttingCount = getStoneCuttingCount(n.type);
                switch(n.type) {
                    case BRICKS -> {
                        ShapedRecipeBuilder.shaped(block, 4)
                                .pattern("xx")
                                .pattern("xx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(consumer);
                        //Tile conversion recipe
                        var tiles = n.getSibling(BlockNode.BlockType.TILES);
                        if (tiles != null) {
                            ShapedRecipeBuilder.shaped(block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', tiles.get())
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                    .save(consumer, modId(n.getName() + "_from_" + tiles.getId().getPath()));
                        }
                    }
                    case CRACKED -> {
                        SimpleCookingRecipeBuilder.smelting(Ingredient.of(parent), block, .1f, 200)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(consumer, smeltingName(block, parent));
                    }
                    case MOSSY -> {
                    }
                    case TILES -> {
                        //Brick conversion recipe
                        var bricks = n.getSibling(BlockNode.BlockType.BRICKS);
                        if (bricks != null) {
                            ShapedRecipeBuilder.shaped(block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', bricks.get())
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                    .save(consumer, modId(n.getName() + "_from_" + bricks.getId().getPath()));
                        }
                        //Default recipe if there are no bricks
                        else {
                            ShapedRecipeBuilder.shaped(block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', parent)
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                    .save(consumer);
                        }
                    }
                    case CHISELED -> {
                    }
                    case PILLAR -> {
                    }
                    case NUB -> {
                    }
                    case SLAB -> {
                        ShapedRecipeBuilder.shaped(block, 6)
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(consumer);
                    }
                    case VERTICAL_SLAB -> {
                        //Special case with stoneCuttingCount here. The slabs make their own conditional stonecutting recipe
                        stoneCuttingCount = 0;
                        var slab = n.getSibling(BlockNode.BlockType.SLAB).get();
                        //Craft from slabs
                        //I have no idea how this shit works
                        ConditionalRecipe.builder().addCondition(APVerticalSlabsCondition.instance)
                                .addRecipe((c) -> {
                                    ShapedRecipeBuilder.shaped(block, 3)
                                            .pattern("x")
                                            .pattern("x")
                                            .pattern("x")
                                            .group("vertical_slab")
                                            .define('x', slab)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                            .save(c);
                                })
                                .build(consumer, modId("vslabs/" + n.getName()));
                        //Revert to slab from vslab
                        ConditionalRecipe.builder().addCondition(APVerticalSlabsCondition.instance)
                                .addRecipe((c) -> {
                                    ShapelessRecipeBuilder.shapeless(slab, 1)
                                            .group("vertical_slab_revert")
                                            .requires(block)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                            .save(c);
                                })
                                .build(consumer, modId("vslabs/" + n.getName() + "_revert"));
                        //Stonecut from block to vslab
                        ConditionalRecipe.builder().addCondition(APVerticalSlabsCondition.instance)
                                .addRecipe((c) -> {
                                    SingleItemRecipeBuilder.stonecutting(getStonecuttingIngredients(n), block, 2)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                            .save(c);
                                })
                                .build(consumer, modId("vslabs/stonecutting/" + n.getName()));


                    }
                    case STAIRS -> {
                        ShapedRecipeBuilder.shaped(block, 4)
                                .pattern("x  ")
                                .pattern("xx ")
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(consumer);
                    }
                    case WALL -> {
                        ShapedRecipeBuilder.shaped(block, 6)
                                .pattern("xxx")
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(consumer);
                    }
                    case FENCE -> {
                        //TODO: fence recipe
                    }
                    case PLATING -> {
                        ShapedRecipeBuilder.shaped(block, 8)
                                .pattern("xxx")
                                .pattern("x x")
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(consumer);
                    }
                    case DARK ->
                        ShapedRecipeBuilder.shaped(block, 8)
                                .pattern("xxx")
                                .pattern("xdx")
                                .pattern("xxx")
                                .define('x', parent)
                                .define('d', Items.BLACK_DYE)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(consumer);
                }
                if (stoneCuttingCount > 0) {
                    SingleItemRecipeBuilder.stonecutting(getStonecuttingIngredients(n), block, stoneCuttingCount)
                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                            .save(consumer, cuttingName(block, parent));
                }

            }

        }));
    }

    private static ResourceLocation smeltingName(Block item, Block from) {
        return new ResourceLocation(ArchitectsPalette.MOD_ID, "smelting/" + item.getRegistryName().getPath());
    }

    private static ResourceLocation cuttingName(Block item, Block from) {
        return new ResourceLocation(ArchitectsPalette.MOD_ID, "stonecutting/" + item.getRegistryName().getPath());
    }

    private static Ingredient getStonecuttingIngredients(BlockNode node) {
        //Traverse the tree in reverse until we hit a step that doesn't use stonecutting.
        var list = new ArrayList<BlockNode>();
        var last = node;
        while (last.parent != null && getStoneCuttingCount(last.type) > 0) {
            list.add(last.parent);
            last = last.parent;
        }
        var stream = list.stream().map((n) -> new ItemStack(n.get().asItem()));
        return Ingredient.of(stream);
    }

    private static Ingredient getParentIngredients(BlockNode node) {
        var stream = node.getParents().stream().map((n) -> new ItemStack(n.get().asItem()));
        return Ingredient.of(stream);
    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(ArchitectsPalette.MOD_ID, name);
    }

    private static int getStoneCuttingCount(BlockNode.BlockType type) {
        return switch (type) {
            case BASE, CRACKED, MOSSY, LAMP, DARK, SPECIAL -> 0;
            case BRICKS, FENCE, TILES, CHISELED, PILLAR, STAIRS, WALL, PLATING -> 1;
            case NUB, SLAB, VERTICAL_SLAB -> 2;
        };
    }

}
