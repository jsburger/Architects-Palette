package architectspalette.core.integration;

import architectspalette.core.integration.advancement.CarveTotemTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class APCriterion {

    public static CarveTotemTrigger CARVE_TOTEM = new CarveTotemTrigger();

    public static void register() {
        CriteriaTriggers.register(CARVE_TOTEM);
    }

}
