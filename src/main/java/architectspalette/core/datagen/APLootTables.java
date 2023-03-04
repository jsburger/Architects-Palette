package architectspalette.core.datagen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import architectspalette.core.registry.util.StoneBlockSet.SetComponent;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class APLootTables extends LootTableProvider {
    public APLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of(
                Pair.of(APBlockLoot::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((location, lootTable) -> LootTables.validate(validationtracker, location, lootTable));
    }

    private static class APBlockLoot extends BlockLoot {
        @Override
        protected Iterable<Block> getKnownBlocks() {
            List<Block> blocks = new LinkedList<>();
            APBlocks.BIRCH_BOARDS.forEach(blocks::add);
            APBlocks.SPRUCE_BOARDS.forEach(blocks::add);
            APBlocks.TREAD_PLATE.forEach((node) -> blocks.add(node.get().get()));
            APBlocks.HAZARD_BLOCK.forEach((node) -> blocks.add(node.get().get()));
            return blocks;
            //return APBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
        }

        @Override
        protected void addTables() {
            processStoneBlockSet(APBlocks.BIRCH_BOARDS);
            processStoneBlockSet(APBlocks.SPRUCE_BOARDS);
            BlockNode.forAllBaseNodes(this::processBlockNode);
//            processBlockNode(APBlocks.TREAD_PLATE);
//            processBlockNode(APBlocks.HAZARD_BLOCK);
        }

        private void slab(Block block) {
            this.add(block, BlockLoot::createSlabItemTable);
        }

        private void processStoneBlockSet(StoneBlockSet set) {
            set.forEachPart((part, block) -> {
                if (part == SetComponent.SLAB) {
                    slab(block);
                }
                else if (part == SetComponent.VERTICAL_SLAB) {
                    slab(block);
                }
                else {
                    this.dropSelf(block);
                }
            });
        }

        private void processBlockNode(BlockNode node) {
            node.forEach((n) -> {
                //Todo: Silk Touch Flag
                var block = n.getBlock();
                switch(n.type) {
                    case SLAB, VERTICAL_SLAB -> slab(block);
                    default -> this.dropSelf(block);
                }
            });
        }
    }
}
