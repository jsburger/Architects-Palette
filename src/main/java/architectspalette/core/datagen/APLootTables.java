package architectspalette.core.datagen;

import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import architectspalette.core.registry.util.StoneBlockSet.SetComponent;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.*;

public class APLootTables extends LootTableProvider {
    public APLootTables(PackOutput pack) {
        super(pack, Collections.emptySet(), List.of(new SubProviderEntry(APBlockLoot::new, LootContextParamSets.BLOCK)));
    }


    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((location, lootTable) -> LootTables.validate(validationtracker, location, lootTable));
    }

    private static class APBlockLoot extends BlockLootSubProvider {

        protected APBlockLoot() {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            BlockNode.forAllBaseNodes(this::processBlockNode);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            List<Block> blocks = new LinkedList<>();
            BlockNode.forAllBaseNodes((node -> node.forEach(n -> blocks.add(n.get()))));
            return blocks;
            //return APBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
        }

        private void slab(Block block) {
            this.add(block, createSlabItemTable(block));
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
                var block = n.get();
                switch(n.type) {
                    case SLAB, VERTICAL_SLAB -> slab(block);
                    default -> this.dropSelf(block);
                }
            });
        }
    }
}
