package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.registry.util.BlockNode;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.ConditionalRecipe;

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
            if (n.parent != null) {
                Block block = n.getBlock();
                Block parent = n.parent.getBlock();

                int stoneCuttingCount = 1;
                switch(n.type) {
                    case BRICKS -> {
                        ShapedRecipeBuilder.shaped(block, 4)
                                .pattern("xx")
                                .pattern("xx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                .save(consumer);
                        //Tile conversion recipe
                        var tiles = n.getSibling(BlockNode.BlockType.TILES);
                        if (tiles != null) {
                            ShapedRecipeBuilder.shaped(block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', tiles.get())
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                    .save(consumer, modId(n.getName() + "_from_" + tiles.getId().getPath()));
                        }
                    }
                    case CRACKED -> {
                        stoneCuttingCount = 0;
                        SimpleCookingRecipeBuilder.smelting(Ingredient.of(parent), block, .1f, 200)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                .save(consumer, smeltingName(block, parent));
                    }
                    case MOSSY -> {
                        stoneCuttingCount = 0;
                    }
                    case TILES -> {
                        //Brick conversion recipe
                        var bricks = n.getSibling(BlockNode.BlockType.BRICKS);
                        if (bricks != null) {
                            ShapedRecipeBuilder.shaped(block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', bricks.get())
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                    .save(consumer, modId(n.getName() + "_from_" + bricks.getId().getPath()));
                        }
                        //Default recipe if there are no bricks
                        else {
                            ShapedRecipeBuilder.shaped(block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', parent)
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                    .save(consumer);
                        }
                    }
                    case CHISELED -> {
                    }
                    case PILLAR -> {
                    }
                    case NUB -> {
                        stoneCuttingCount = 2;
                    }
                    case SLAB -> {
                        stoneCuttingCount = 2;
                        ShapedRecipeBuilder.shaped(block, 6)
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                .save(consumer);
                    }
                    case VERTICAL_SLAB -> {
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
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                            .save(c);
                                })
                                .build(consumer, modId("vslabs/" + n.getName()));
                        //Revert to slab from vslab
                        ConditionalRecipe.builder().addCondition(APVerticalSlabsCondition.instance)
                                .addRecipe((c) -> {
                                    ShapelessRecipeBuilder.shapeless(slab, 1)
                                            .group("vertical_slab_revert")
                                            .requires(block)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                            .save(c);
                                })
                                .build(consumer, modId("vslabs/" + n.getName() + "_revert"));
                        //Stonecut from block to vslab
                        ConditionalRecipe.builder().addCondition(APVerticalSlabsCondition.instance)
                                .addRecipe((c) -> {
                                    SingleItemRecipeBuilder.stonecutting(getParentIngredients(n), block, 2)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
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
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                .save(consumer);
                    }
                    case WALL -> {
                        ShapedRecipeBuilder.shaped(block, 6)
                                .pattern("xxx")
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
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
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
                                .save(consumer);
                    }
                }
                if (stoneCuttingCount > 0) {
                    SingleItemRecipeBuilder.stonecutting(getParentIngredients(n), block, stoneCuttingCount)
                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.getBlock()))
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

    private static Ingredient getParentIngredients(BlockNode node) {
        //I don't want to talk about it.
        var stream = node.getParents().stream().map(BlockNode::getBlock).map(Block::asItem).map(ItemStack::new);
        return Ingredient.of(stream);
    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(ArchitectsPalette.MOD_ID, name);
    }
}
