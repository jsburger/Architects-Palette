package architectspalette.core.integration.advancement;

import architectspalette.core.ArchitectsPalette;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public class CarveTotemTrigger extends AbstractCriterionTrigger<CarveTotemTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(ArchitectsPalette.MOD_ID, "carve_totem");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        this.triggerListeners(player, Instance::test);
    }

    @Override
    protected Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate player, ConditionArrayParser conditionsParser) {
        return new CarveTotemTrigger.Instance(player);
    }

    public static class Instance extends CriterionInstance {

        public Instance(EntityPredicate.AndPredicate player) {
            super(CarveTotemTrigger.ID, player);
        }

        public static CarveTotemTrigger.Instance simple() {
            return new CarveTotemTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND);
        }

        public boolean test() {
            return true;
        }
    }
}
