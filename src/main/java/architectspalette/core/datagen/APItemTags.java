package architectspalette.core.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class APItemTags extends ItemTagsProvider {
    public APItemTags(DataGenerator generator, BlockTagsProvider blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
    }

}
