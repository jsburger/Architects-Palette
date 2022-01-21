package architectspalette.core.integration;

import architectspalette.common.blocks.VerticalSlabBlock;
import architectspalette.core.ArchitectsPalette;
import architectspalette.core.config.APConfig;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;

public class APVerticalSlabsCondition implements ICondition {

	private static final ResourceLocation ID = new ResourceLocation(ArchitectsPalette.MOD_ID, "enable_vertical_slabs");
	
	@Override
	public ResourceLocation getID() {
		return ID;
	}

	/*
	 * Original logic from Abnormals Core
	 * https://github.com/team-abnormals/abnormals-core/blob/264b7ca6df505743f1c969547dbf2bc8e71b04d5/src/main/java/com/minecraftabnormals/abnormals_core/core/api/conditions/QuarkFlagRecipeCondition.java
	 */
	@Override
	public boolean test() {
		if(ModList.get().isLoaded(VerticalSlabBlock.QUARK_ID)) {
			JsonObject dummyObject = new JsonObject();
			dummyObject.addProperty("type", "quark:flag");
			dummyObject.addProperty("flag", "vertical_slabs");
			return CraftingHelper.getCondition(dummyObject).test();
		}
		return APConfig.VERTICAL_SLABS_FORCED.get();
	}
	
	public static class Serializer implements IConditionSerializer<APVerticalSlabsCondition> {

		@Override
		public void write(JsonObject json, APVerticalSlabsCondition value) {
			// NO-OP
		}

		@Override
		public APVerticalSlabsCondition read(JsonObject json) {
			return new APVerticalSlabsCondition();
		}

		@Override
		public ResourceLocation getID() {
			return ID;
		}
		
	}

}
