package architectspalette.core.loot;

import architectspalette.core.ArchitectsPalette;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class WitheredBoneLootModifier extends LootModifier {
    private final Item replacedItem;
    private final Item boneItem;

    public static final Serializer SERIALIZER = new Serializer();

    protected WitheredBoneLootModifier(LootItemCondition[] conditionsIn, Item replacedItem, Item boneItem) {
        super(conditionsIn);
        this.replacedItem = replacedItem;
        this.boneItem = boneItem;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
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

    private static class Serializer extends GlobalLootModifierSerializer<WitheredBoneLootModifier> {

        Serializer(){
            this.setRegistryName(new ResourceLocation(ArchitectsPalette.MOD_ID, "wither_skeleton_bones"));
        }

        @Override
        public WitheredBoneLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            String boneItem = GsonHelper.getAsString(object, "bone");
            String replacedItem = GsonHelper.getAsString(object, "replaces");

            Item witheredBone = ForgeRegistries.ITEMS.getValue(new ResourceLocation(boneItem));
            Item bone         = ForgeRegistries.ITEMS.getValue(new ResourceLocation(replacedItem));

            return new WitheredBoneLootModifier(ailootcondition, bone, witheredBone);
        }

        @Override
        public JsonObject write(WitheredBoneLootModifier instance) {
            JsonObject obj;
            if (instance.conditions.length > 0) {
                obj = makeConditions(instance.conditions);
            }
            else {
                obj = new JsonObject();
            }
            obj.addProperty("bone", instance.boneItem.getRegistryName().toString());
            obj.addProperty("replaces", instance.replacedItem.getRegistryName().toString());
            return obj;
        }
    }
}
