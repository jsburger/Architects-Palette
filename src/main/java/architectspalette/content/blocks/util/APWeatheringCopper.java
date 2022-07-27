package architectspalette.content.blocks.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.Optional;
import java.util.function.Supplier;

//If you can't beat 'em, join 'em. (Vanilla Copper interface isn't extendable.)
public interface APWeatheringCopper extends ChangeOverTimeBlock<WeatheringCopper.WeatherState> {
    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() ->
    {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.put(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER);
        builder.put(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER);
        builder.put(Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
        builder.put(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER);
        builder.put(Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER);
        builder.put(Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
        builder.put(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB);
        builder.put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB);
        builder.put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
        builder.put(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS);
        builder.put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS);
        builder.put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);
        return builder.build();
    });

    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    Supplier<BiMap<Block, Block>> WAXED_BY_BLOCK = Suppliers.memoize(() ->
    {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.put(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER);
        builder.put(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER);
        builder.put(Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
        builder.put(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER);
        builder.put(Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER);
        builder.put(Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
        builder.put(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB);
        builder.put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB);
        builder.put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
        builder.put(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS);
        builder.put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS);
        builder.put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);
        return builder.build();
    });

    Supplier<BiMap<Block, Block>> UNWAXED_BY_BLOCK = Suppliers.memoize(() -> WAXED_BY_BLOCK.get().inverse());


    static Optional<Block> getPrevious(Block block) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(block));
    }

    static Optional<Block> getWaxed(Block block) {
        return Optional.ofNullable(WAXED_BY_BLOCK.get().get(block));
    }
    static Optional<Block> getUnWaxed(Block block) {
        return Optional.ofNullable(UNWAXED_BY_BLOCK.get().get(block));
    }

    static Block getFirst(Block baseBlock) {
        Block block = baseBlock;

        for(Block block1 = PREVIOUS_BY_BLOCK.get().get(block); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
            block = block1;
        }

        return block;
    }

    default float getChanceModifier() {
        return this.getAge() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }

    static Optional<Block> getNext(Block block) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(block));
    }

}
