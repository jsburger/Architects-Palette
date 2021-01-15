package architectspalette.core.data;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.integration.advancement.CarveTotemTrigger;
import architectspalette.core.registry.APBlocks;
import com.google.common.collect.Sets;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.PlacedBlockTrigger;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;


// This was heavily referenced from Farmer's Delight by vectorwing, you might notice a trend here.
// Thanks again!
public class Advancements extends AdvancementProvider {

    private final Path PATH;

    public Advancements(DataGenerator generatorIn) {
        super(generatorIn);
        PATH = generatorIn.getOutputFolder();
    }

    @Override
    public void act(DirectoryCache cache) {
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate Advancement" + advancement.getId());
            }
            else {
                Path path1 = getPath(PATH, advancement);
                try {
                    IDataProvider.save((new GsonBuilder()).setPrettyPrinting().create(), cache, advancement.copy().serialize(), path1);
                }
                catch (IOException ioException){
                    ArchitectsPalette.LOGGER.error("Couldn't save advancement {}", path1, ioException);
                }
            }
        };

        new APAdvancements().accept(consumer);
    }

    // Feels kinda bad to copy even convenience scripts, but like, it's just a good idea to have this.
    // I feel clever enough having done Abyssaline, so I'll just let myself have it.
    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    public static class APAdvancements implements Consumer<Consumer<Advancement>> {

        private static IFormattableTextComponent getTranslationKey(String key, Object... args) {
            return new TranslationTextComponent(ArchitectsPalette.MOD_ID + "." + key, args);
        }

        private String getNameId(String id) {
            return ArchitectsPalette.MOD_ID + ":" + id;
        }

        // Not even gonna skirt around the fact that I straight up copy pasted this part. vectorwing seriously thought of everything.
        protected static Advancement.Builder getAdvancement(Advancement parent, IItemProvider display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.builder().withParent(parent).withDisplay(display,
                getTranslationKey("advancement." + name),
                getTranslationKey("advancement." + name + ".desc"),
                null, frame, showToast, announceToChat, hidden);
        }

        protected Advancement buyAdvancement(Advancement parent, IItemProvider display, String name, String item, Consumer<Advancement> advancementConsumer) {
            return getAdvancement(parent, display, name, FrameType.TASK, true, true, false)
                    .withCriterion(item, InventoryChangeTrigger.Instance.forItems(display))
                    .register(advancementConsumer, getNameId("main/" + name));
        }

        @Override
        public void accept(Consumer<Advancement> advancementConsumer) {
            Advancement architectsPalette = Advancement.Builder.builder()
                    .withDisplay(APBlocks.CHISELED_ABYSSALINE_BRICKS.get().asItem(),
                            getTranslationKey("advancement.root"),
                            getTranslationKey("advancement.root.desc"),
                            new ResourceLocation("architects_palette:textures/block/limestone_bricks.png"),
                            FrameType.TASK, false, false, false)
                    .withCriterion("craftingtable", InventoryChangeTrigger.Instance.forItems(new IItemProvider[]{}))
                    .register(advancementConsumer, getNameId("main/root"));

            Advancement totemCarving = getAdvancement(architectsPalette, APBlocks.PLACID_ACACIA_TOTEM.get().asItem(), "totem_carving", FrameType.TASK, true ,true, false)
                    .withCriterion("carve_totem", CarveTotemTrigger.Instance.simple())
                    .register(advancementConsumer, getNameId("main/totem_carving"));

            Advancement whatACatch = getAdvancement(architectsPalette, APBlocks.COD_LOG.get().asItem(), "buy_fish_block", FrameType.TASK, true, true, false)
                    .withCriterion("cod_log", InventoryChangeTrigger.Instance.forItems(APBlocks.COD_LOG.get().asItem()))
                    .withCriterion("salmon_log", InventoryChangeTrigger.Instance.forItems(APBlocks.SALMON_LOG.get().asItem()))
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .register(advancementConsumer, getNameId("main/buy_fish_block"));

            Advancement buyPipes = buyAdvancement(architectsPalette, APBlocks.PIPE.get().asItem(), "buy_pipe", "pipe", advancementConsumer);
            Advancement buyEntrails = buyAdvancement(architectsPalette, APBlocks.ENTRAILS.get().asItem(), "buy_entrails", "entrails", advancementConsumer);
            Advancement placeEntrails = getAdvancement(buyEntrails, APBlocks.ENTRAILS.get().asItem(), "place_entrails", FrameType.TASK, true, true, true)
                    .withCriterion("entrails", PlacedBlockTrigger.Instance.placedBlock(APBlocks.ENTRAILS.get()))
                    .register(advancementConsumer, getNameId("main/place_entrails"));

            Advancement buyPlating = buyAdvancement(architectsPalette, APBlocks.PLATING_BLOCK.get().asItem(), "buy_plating", "plating", advancementConsumer);
            Advancement buySpool = buyAdvancement(architectsPalette, APBlocks.SPOOL.get().asItem(), "buy_spool", "spool", advancementConsumer);

            Advancement buyCelestialStone = getAdvancement(architectsPalette, APBlocks.SUNSTONE.get().asItem(), "buy_celestial_stone", FrameType.TASK, true, true, false)
                    .withCriterion("moonstone", InventoryChangeTrigger.Instance.forItems(APBlocks.MOONSTONE.get().asItem()))
                    .withCriterion("sunstone", InventoryChangeTrigger.Instance.forItems(APBlocks.SUNSTONE.get().asItem()))
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .register(advancementConsumer, getNameId("main/buy_celestial_stone"));

            Advancement getWarpstone = buyAdvancement(architectsPalette, APBlocks.WARPSTONE.get().asItem(), "find_warpstone", "warpstone", advancementConsumer);
            Advancement getTwistedSapling = buyAdvancement(getWarpstone, APBlocks.TWISTED_SAPLING.get().asItem(), "find_twisted_sapling", "sapling", advancementConsumer);

        }
    }

}
