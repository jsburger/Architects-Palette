package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class APItemTags extends ItemTagsProvider {
    public APItemTags(PackOutput pack, CompletableFuture<HolderLookup.Provider> cf, TagsProvider<Block> blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pack, cf, blockTagsProvider.contentsGetter(), ArchitectsPalette.MOD_ID, existingFileHelper);
        //super(generator, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
    }


}
