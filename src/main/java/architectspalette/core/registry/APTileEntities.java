package architectspalette.core.registry;

import com.google.common.collect.Sets;

import architectspalette.common.tileentity.*;
import architectspalette.core.ArchitectsPalette;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class APTileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ArchitectsPalette.MOD_ID);

	public static final RegistryObject<TileEntityType<ChiseledAbyssalineTileEntity>> CHISELED_ABYSSALINE = TILE_ENTITY_TYPES.register("chiseled_abyssaline", () -> new TileEntityType<>(ChiseledAbyssalineTileEntity::new, Sets.newHashSet(APBlocks.CHISELED_ABYSSALINE_BRICKS.get()), null));
}