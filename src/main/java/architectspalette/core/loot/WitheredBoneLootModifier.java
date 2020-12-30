package architectspalette.core.loot;

import architectspalette.core.ArchitectsPalette;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class WitheredBoneLootModifier extends LootModifier {
    private final Item replacedItem;
    private final Item boneItem;

    public static final Serializer SERIALIZER = new Serializer();

    protected WitheredBoneLootModifier(ILootCondition[] conditionsIn, Item replacedItem, Item boneItem) {
        super(conditionsIn);
        this.replacedItem = replacedItem;
        this.boneItem = boneItem;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Entity t = context.get(LootParameters.THIS_ENTITY);
        if (t == null) return generatedLoot;
        if (t.getType().delegate.name().equals(new ResourceLocation("minecraft:wither_skeleton"))) {
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
        public WitheredBoneLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            String boneItem = JSONUtils.getString(object, "bone");
            String replacedItem = JSONUtils.getString(object, "replaces");

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
            obj.addProperty("bone", instance.boneItem.getItem().getRegistryName().toString());
            obj.addProperty("replaces", instance.replacedItem.getItem().getRegistryName().toString());
            return obj;
        }
    }
}
