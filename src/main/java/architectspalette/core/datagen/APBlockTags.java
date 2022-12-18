package architectspalette.core.datagen;

import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.APBlocks.HADALINE_PLATING;

public class APBlockTags extends BlockTagsProvider {
    public APBlockTags(DataGenerator generator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        registerMiningTags();
    }

    @SafeVarargs
    private void tagBlocks(TagKey<Block> tagKey, RegistryObject<? extends Block>... registryObjects) {
        TagAppender<Block> tag = tag(tagKey);
        Arrays.stream(registryObjects).map(RegistryObject::get).forEach(tag::add);
    }

    private void registerMiningTags() {
        //Handle all sets
        StoneBlockSet.forAllSets(set -> {
            TagAppender<Block> tag = tag(set.miningTag);
            set.forEach(tag::add);
            //TODO: Commented out for now because I don't need to mess with it atm
//            if (set.miningLevel != null) {
//                TagAppender<Block> levelTag = tag(set.miningLevel);
//                set.forEach(levelTag::add);
//            }
        });

        tagBlocks(BlockTags.MINEABLE_WITH_PICKAXE,
                ABYSSALINE,
                CHISELED_ABYSSALINE_BRICKS,
                ABYSSALINE_PILLAR,
                ABYSSALINE_LAMP_BLOCK,
                HADALINE,
//TODO          CHISELED_HADALINE_BRICKS,
                HADALINE_PILLAR,
                HADALINE_LAMP_BLOCK,
                HADALINE_PLATING,
                PIPE,
                OLIVESTONE_PILLAR,
                CRACKED_OLIVESTONE_BRICKS,
                CRACKED_OLIVESTONE_TILES,
                CHISELED_OLIVESTONE,
                ILLUMINATED_OLIVESTONE,
                CRACKED_ALGAL_BRICKS,
                CHISELED_ALGAL_BRICKS,
                ALGAL_LAMP,
                FLINT_BLOCK,
                FLINT_PILLAR,
                CHISELED_PACKED_ICE,
                PACKED_ICE_PILLAR,
                CHISELED_SUNMETAL_BLOCK,
                SUNMETAL_PILLAR,
                SUNMETAL_BARS,
                OSSEOUS_PILLAR,
                OSSEOUS_SKULL,
                LIT_OSSEOUS_SKULL,
                WITHERED_BONE_BLOCK,
                WITHERED_OSSEOUS_PILLAR,
                WITHERED_OSSEOUS_SKULL,
                LIT_WITHERED_OSSEOUS_SKULL,
                WITHER_LAMP,
                ENTWINE_PILLAR,
                CHISELED_ENTWINE,
                ENTWINE_BARS,
                HEAVY_STONE_BRICKS,
                HEAVY_MOSSY_STONE_BRICKS,
                HEAVY_CRACKED_STONE_BRICKS,
                RUNIC_GLOWSTONE,
                SCUTE_BLOCK,
                ROTTEN_FLESH_BLOCK,
                GILDED_SANDSTONE_PILLAR,
                CHISELED_GILDED_SANDSTONE,
                WEEPING_BLACKSTONE,
                TWISTING_BLACKSTONE,
                WEEPING_BLACKSTONE_BRICKS,
                TWISTING_BLACKSTONE_BRICKS,
                CHORAL_END_STONE_BRICKS,
                CRACKED_END_STONE_BRICKS,
                CHISELED_END_STONE_BRICKS,
                POTTED_TWISTED_SAPLING,
                CRACKED_BASALT_TILES,
                CHISELED_BASALT_TILES,
                SUNSTONE,
                MOONSTONE,
                MOLTEN_NETHER_BRICKS,
                HEAVY_END_STONE_BRICKS,
                HEAVY_CRACKED_END_STONE_BRICKS,
                REDSTONE_CAGE_LANTERN,
                GLOWSTONE_CAGE_LANTERN,
                ALGAL_CAGE_LANTERN,
                DRIPSTONE_PILLAR,
                CHISELED_DRIPSTONE,
                HEAVY_DRIPSTONE_BRICKS,
                DRIPSTONE_LAMP,
                CALCITE_PILLAR,
                CHISELED_CALCITE,
                HEAVY_CALCITE_BRICKS,
                CALCITE_LAMP,
                TUFF_PILLAR,
                CHISELED_TUFF,
                HEAVY_TUFF_BRICKS,
                TUFF_LAMP,
                HELIODOR_ROD,
                EKANITE_ROD,
                MONAZITE_ROD,
                UNOBTANIUM_BLOCK,
                NETHER_BRASS_PILLAR,
                NETHER_BRASS_CHAIN,
                NETHER_BRASS_LANTERN,
                ESOTERRACK_PILLAR,
                ONYX_PILLAR,
                CHISELED_WARDSTONE,
                WARDSTONE_PILLAR,
                WARDSTONE_LAMP,
                STONE_NUB,
                SMOOTH_STONE_NUB,
                SANDSTONE_NUB,
                ANDESITE_NUB,
                GRANITE_NUB,
                DIORITE_NUB,
                BLACKSTONE_NUB,
                DEEPSLATE_NUB,
                BONE_NUB,
                NUB_OF_ENDER,
                IRON_NUB,
                GOLD_NUB,
                DIAMOND_NUB,
                EMERALD_NUB,
                NETHERITE_NUB,
                HAZARD_SIGN
        );
    }


}
