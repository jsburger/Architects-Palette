package architectspalette.core.registry;

import architectspalette.content.blocks.CageLanternBlock;
import architectspalette.content.blocks.SunstoneBlock;
import architectspalette.content.blocks.abyssaline.*;
import architectspalette.core.registry.util.BlockNode;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.stream.Stream;

import static architectspalette.core.registry.APBlocks.*;

public class APBlockProperties {
	private static Block.Properties AbyssalineBase() {
		return Block.Properties.copy(Blocks.OBSIDIAN).strength(25.0F, 600.0F)
			.emissiveRendering(AbyssalineHelper::needsPostProcessing)
			.hasPostProcess(AbyssalineHelper::needsPostProcessing)
			.isValidSpawn(AbyssalineHelper::allowsMobSpawning)
			.lightLevel(AbyssalineBlock::getLightValue);
	}

	public static final Block.Properties ABYSSALINE = AbyssalineBase();

	public static final Block.Properties ABYSSALINE_LAMP = AbyssalineBase()
			.lightLevel(AbyssalineLampBlock::getLightValue);

	public static final Block.Properties CHISELED_ABYSSALINE = AbyssalineBase()
			.lightLevel(ChiseledAbyssalineBlock::getLightValue);

	public static final Block.Properties ABYSSALINE_NUB = AbyssalineBase()
			.lightLevel(AbyssalineNubBlock::getLightValue);


	public static Block.Properties Meat(MapColor color) {
		return Block.Properties.of().mapColor(color).strength(1.0F).sound(SoundType.CORAL_BLOCK);
	}

	private static Block.Properties stoneBase() {
		return BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.STONE);
	}
	private static Block.Properties metalBase() {
		return BlockBehaviour.Properties.of().mapColor(MapColor.METAL);
	}
	private static Block.Properties woodBase() {
		return BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
				.ignitedByLava()
				.instrument(NoteBlockInstrument.BASS)
				.strength(2.0F)
				.sound(SoundType.WOOD);
	}

	public static final Block.Properties FLINT = stoneBase().mapColor(MapColor.COLOR_GRAY).strength(3.0F, 16.0F).requiresCorrectToolForDrops();
	public static final Block.Properties MYONITE = Block.Properties.copy(Blocks.STONE);
	public static final Block.Properties SUNMETAL = Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0F, 8.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops();
	 // Should be less slippery?
	public static final Block.Properties BUILDING_ICE = Block.Properties.copy(Blocks.PACKED_ICE).friction(0.8F);
	 // As Prismarine
	public static final Block.Properties OLIVESTONE = stoneBase().mapColor(MapColor.TERRACOTTA_GREEN).strength(1.5F, 6.0F).requiresCorrectToolForDrops();
	 // As Nether Bricks
	public static final Block.Properties ALGAL_BRICK = stoneBase().mapColor(MapColor.TERRACOTTA_CYAN).strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops();

	public static final Block.Properties ENTWINE = stoneBase().mapColor(MapColor.COLOR_CYAN).strength(3.0F, 6.0F).sound(APSounds.APSoundTypes.ENTWINE).requiresCorrectToolForDrops();
	public static final Block.Properties ENDER_PEARL = stoneBase().mapColor(MapColor.COLOR_CYAN).strength(1.5F).sound(APSounds.APSoundTypes.ENDER_PEARL);
	public static final BlockBehaviour.Properties PLATING = metalBase().mapColor(MapColor.STONE).strength(4.0F, 10.0F).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops();
	public static final BlockBehaviour.Properties NETHER_CRYSTAL = BlockBehaviour.Properties.copy(Blocks.GLASS).lightLevel(e -> 12).requiresCorrectToolForDrops().strength(1.2f);
	public static final BlockBehaviour.Properties NETHER_BRASS = metalBase().mapColor(MapColor.COLOR_YELLOW).strength(4.0F, 10.0F).sound(SoundType.COPPER).requiresCorrectToolForDrops();

	public static final BlockBehaviour.Properties ESOTERRACK = stoneBase().mapColor(MapColor.RAW_IRON).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NETHERRACK);
	public static final BlockBehaviour.Properties ONYX = stoneBase().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6).sound(SoundType.STONE);
	public static final BlockBehaviour.Properties WARDSTONE = stoneBase().mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(2F, 6).sound(SoundType.NETHER_BRICKS);
	public static final BlockBehaviour.Properties MOONSHALE = stoneBase().mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.5F, 6.0F);
	public static final BlockBehaviour.Properties NEBULITE = stoneBase().mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(1.5F, 6.0F);

	public static final BlockBehaviour.Properties ANCIENT_PLATING = metalBase().mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(4.0F, 12.0F).sound(SoundType.NETHERITE_BLOCK);

	public static final BlockBehaviour.Properties GREEN_FIRE = BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_LIGHT_GREEN)
			.noCollission()
			.instabreak()
			.lightLevel((p_50884_) -> 13)
			.sound(SoundType.WOOL)
			.noLootTable()
			.pushReaction(PushReaction.DESTROY);

	public static final BlockBehaviour.Properties BRASS_TORCH = BlockBehaviour.Properties.of()
			.noCollission()
			.instabreak()
			.lightLevel((p_50884_) -> 13)
			.pushReaction(PushReaction.DESTROY)
			.sound(SoundType.WOOD);

	public static final BlockBehaviour.Properties MOLTEN_BRICK = stoneBase()
			.mapColor(MapColor.NETHER)
			.requiresCorrectToolForDrops()
			.strength(2.0F, 6.0F)
			.lightLevel((state) -> 3)
			.hasPostProcess((a, b, c) -> true)
			.emissiveRendering((a, b, c) -> true);

	public static final BlockBehaviour.Properties CAGE_LANTERN = BlockBehaviour.Properties.of()
			.mapColor(MapColor.METAL)
			.requiresCorrectToolForDrops()
			.emissiveRendering((state, reader, pos) -> state.getValue(CageLanternBlock.LIT))
			.hasPostProcess((state, reader, pos) -> state.getValue(CageLanternBlock.LIT))
			.strength(3.5f)
			.sound(SoundType.LANTERN)
			.noOcclusion();

	public static final BlockBehaviour.Properties ACACIA_TOTEM = BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_ORANGE)
			.ignitedByLava()
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F)
			.sound(SoundType.WOOD);

	public static final BlockBehaviour.Properties ORACLE = BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK)
			.sound(SoundType.STONE);

	public static final BlockBehaviour.Properties ORACLE_LAMP = BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK)
			.sound(SoundType.STONE)
			.lightLevel((state) -> 16);

	public static final BlockBehaviour.Properties CEREBRAL = BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS)
			.sound(SoundType.DEEPSLATE_TILES);

	// This makes a new property each time so that setting the door to not solid doesn't interfere.
	// That might not be a thing but I don't care to come up with a way of checking.
	public static BlockBehaviour.Properties TwistedWood() {
		return woodBase().mapColor(MapColor.COLOR_PURPLE).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	}
	public static BlockBehaviour.Properties TwistedWood(boolean redstoneComponent) {
		BlockBehaviour.Properties p = TwistedWood();
		if (redstoneComponent) {
			return p.noCollission().strength(0.5f);
		}
		return p;
	}

	public static final BlockBehaviour.Properties SUNSTONE = BlockBehaviour.Properties.copy(Blocks.BASALT)
//			.setOpaque(SunstoneBlock::isOpaque).variableOpacity()
			.lightLevel(SunstoneBlock::lightValue);
			//Causes really wack lighting
//			.setEmmisiveRendering(SunstoneBlock::isLit).setNeedsPostProcessing(SunstoneBlock::isLit);


	public static void registerFlammables() {
		// Logs & Coal: 5, 5
		// Planks: 5, 20
		// Leaves & Wool: 30, 60
		// Plants: 60, 100
		registerFlammable(TWISTED_FENCE.get(), 5, 20);
		TWISTED_PLANKS.registerFlammable(5, 20);
		Stream.of(GRINNING_ACACIA_TOTEM, PLACID_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM,
				TWISTED_LOG, STRIPPED_TWISTED_LOG, TWISTED_WOOD, STRIPPED_TWISTED_WOOD,
				SPOOL, CHARCOAL_BLOCK,
				OAK_RAILING, BIRCH_RAILING, SPRUCE_RAILING,
				ACACIA_RAILING, DARK_OAK_RAILING, JUNGLE_RAILING,
				TWISTED_RAILING
		).forEach((t) -> {
			registerFlammable(t.get(), 5, 5);
		});
		for (BlockNode node : boards) {
			node.forEach((n) -> {
				registerFlammable(n.get(), 5, 20);
			});
		}
	}



	final static FireBlock fire = (FireBlock) Blocks.FIRE;
    public static void registerFlammable(Block block, Integer encouragement, Integer flammability) {
        fire.setFlammable(block, encouragement, flammability);
    }
}