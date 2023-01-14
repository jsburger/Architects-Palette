package architectspalette.core.loot;

import architectspalette.core.ArchitectsPalette;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class WitheredBoneLootModifier extends LootModifier {
    private final Item replacedItem;
    private final Item boneItem;

    public static final Supplier<Codec<WitheredBoneLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
        .and(
            inst.group(
                ForgeRegistries.ITEMS.getCodec().fieldOf("replaces").forGetter((m) -> m.replacedItem),
                ForgeRegistries.ITEMS.getCodec().fieldOf("bone").forGetter((m) -> m.boneItem)
            )
        )
        .apply(inst, WitheredBoneLootModifier::new)));

    protected WitheredBoneLootModifier(LootItemCondition[] conditionsIn, Item replacedItem, Item boneItem) {
        super(conditionsIn);
        this.replacedItem = replacedItem;
        this.boneItem = boneItem;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Entity t = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (t == null) return generatedLoot;
        if (t instanceof WitherSkeleton) {
            int amountOfBones = 0;
            for (ItemStack i : generatedLoot) {
                //check if item is the item to replace, take note of how many there are
                if (i.getItem() == replacedItem) {
                    amountOfBones += i.getCount();
                }
            }
            generatedLoot.removeIf(i -> i.getItem() == replacedItem);
            generatedLoot.add(new ItemStack(boneItem, amountOfBones));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC.get();
    }
}
