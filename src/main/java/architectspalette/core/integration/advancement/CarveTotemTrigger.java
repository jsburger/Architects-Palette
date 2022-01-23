package architectspalette.core.integration.advancement;

import architectspalette.core.ArchitectsPalette;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CarveTotemTrigger extends SimpleCriterionTrigger<CarveTotemTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(ArchitectsPalette.MOD_ID, "carve_totem");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Instance::test);
    }

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext conditionsParser) {
        return new CarveTotemTrigger.Instance(player);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        public Instance(EntityPredicate.Composite player) {
            super(CarveTotemTrigger.ID, player);
        }

        public static CarveTotemTrigger.Instance simple() {
            return new CarveTotemTrigger.Instance(EntityPredicate.Composite.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}
