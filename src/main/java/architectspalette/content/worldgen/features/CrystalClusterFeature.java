package architectspalette.content.worldgen.features;

import architectspalette.content.worldgen.features.configs.CrystalClusterConfig;
import architectspalette.core.registry.MiscRegistry;
import org.joml.Vector3f;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class CrystalClusterFeature extends Feature<CrystalClusterConfig> {

    public CrystalClusterFeature(Codec<CrystalClusterConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CrystalClusterConfig> context) {

        //Check for free space
        if (!context.level().isEmptyBlock(context.origin())) return false;
        if (context.config().hanging) {
            //Check for being on a ceiling
            if (context.level().isEmptyBlock(context.origin().above())) return false;
        }
        else {
            //Check for being on a floor
            if (context.level().isEmptyBlock(context.origin().below())) return false;
        }

        CrystalClusterConfig config = context.config();
        RandomSource random = context.random();

        //Set horizontal angle used to skew the shelves of crystals
        Vector3f shelfAngle = new Vector3f(-1, 0, 0);
        shelfAngle.rotateY((random.nextFloat() * 2 * (float)(Math.PI)));

        //Set horizontal angle that determines the spaces between shelves
        Vector3f formationAngle = new Vector3f(shelfAngle);
        formationAngle.rotateY((float)Math.toDegrees(fRandomRange(random,-15, 15) + 90));

        Vector3f placePos = new Vector3f(context.origin().getX(), context.origin().getY(), context.origin().getZ());

        List<BlockPos> posList = new java.util.ArrayList<>(List.of());
        //4-7 shelves
        int shelves = iRandomRange(random, 4, 7);
        for (int i = 0; i < shelves; i++) {

            float scale = ((float)i+1)/shelves;
            //1-7 pillars each shelf
            int pillars = iRandomRange(random,1, 7);
            placeShelf(BlockPos.containing(placePos.x(), placePos.y(), placePos.z()), pillars, shelfAngle, scale, context, posList);

            //Offset next shelf position
            formationAngle.normalize();
            formationAngle.mul(fRandomRange(random, .5f, 2));
            placePos.add(formationAngle);
        }
        for (BlockPos pos: posList) {
            tryPlaceExtrusion(pos, context.level(), config.extrusionState, config.crystalState.getBlock(), config.hanging ? 1 : -1, random);
        }

        return true;
    }

    private static void placeShelf(BlockPos startPos, int crystals, Vector3f shelfAngle, float shelfScale, FeaturePlaceContext<CrystalClusterConfig> context, List<BlockPos> crystalList) {
        RandomSource random = context.random();
        CrystalClusterConfig config = context.config();
        WorldGenLevel world = context.level();

        int flip = config.hanging ? 1 : -1;
        Vector3f placePos = new Vector3f(startPos.getX(), startPos.getY(), startPos.getZ());
        placePos.add(0, -2 * flip, 0);

        for (int i = 0; i < crystals; i++) {

            Vector3f offset = new Vector3f(shelfAngle);
            offset.mul(fRandomRange(random, .5f, 2.5f));

            placePos.add(offset);

            int pillarLength = config.minLength + random.nextInt((int)(Math.floor((config.maxLength - config.minLength) * shelfScale)) + 1);
            placePillar(new BlockPos.MutableBlockPos(placePos.x(), placePos.y(), placePos.z()), pillarLength, world, context, flip, crystalList);
            if (pillarLength > (config.maxLength - config.minLength)/2 && random.nextBoolean()) {
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(
                        placePos.x() + iRandomRange(random, -1, 1),
                        placePos.y() + iRandomRange(random, -1, 1),
                        placePos.z() + iRandomRange(random, -1, 1));
                placePillar(pos, pillarLength / 2, world, context, flip, crystalList);
            }
        }
    }

    private static void placePillar(BlockPos.MutableBlockPos placePos, int length, WorldGenLevel world, FeaturePlaceContext<CrystalClusterConfig> context, int flip, List<BlockPos> crystalList) {
        CrystalClusterConfig config = context.config();
        // Go up until a ceiling is found, give up if no ceiling found
        int tries = 10;
        while (canReplaceAt(world, placePos)) {
            placePos.setY(placePos.getY() + flip);
            if (world.getBlockState(placePos).is(Blocks.LAVA)) return;
            if (tries-- == 0) {
                return;
            }
        }
        //Move out of ceilings
        tries = 5;
        while (!canReplaceAt(world, placePos)) {
            placePos.setY(placePos.getY() - flip);
            if (tries-- == 0) {
                return;
            }
        }
        //Place Pillar
        boolean doExtrusion = true;
        while (--length >= 0) {
            if (!canReplaceAt(world, placePos)) return;
            if (doExtrusion) {
                crystalList.add(placePos.immutable());
//                tryPlaceExtrusion(placePos.immutable(), world, config.extrusionState, config.crystalState.getBlock(), flip, context.random());
                doExtrusion = false;
            }
            world.setBlock(placePos, config.crystalState, 2);
            placePos.setY(placePos.getY() - flip);
        }
    }

    private static void tryPlaceExtrusion(BlockPos startPos, WorldGenLevel level, BlockState placeState, Block avoidBlock, int flip, RandomSource random) {
        for (Direction dir : Direction.values()) {
            if (dir.getAxis() != Direction.Axis.Y) {
                if (random.nextInt(3) != 1) continue;
                BlockPos pos = startPos.relative(dir, 1);
                while (level.getBlockState(pos).is(placeState.getBlock()) && Math.abs(startPos.getY() - pos.getY()) <= 4) pos = pos.below(flip);
                BlockPos above = pos.above();
                BlockPos below = pos.below();
                if (canReplaceAt(level, above) ^ canReplaceAt(level, below)) {
                    if (!level.getBlockState(above).is(avoidBlock) && !level.getBlockState(below).is(avoidBlock)) {
                        for (int i = iRandomRange(random, 1, 2); i > 0; i--) {
                            if (canReplaceAt(level, pos)) {
                                level.setBlock(pos, placeState, 2);
                                pos = pos.below(flip);
                            }
                            else break;
                        }
                    }
                }
            }
        }
    }

    private static int iRandomRange(RandomSource random, int min, int max) {
        return min + (random.nextInt((max - min) + 1));
    }

    private static float fRandomRange(RandomSource random, float min, float max) {
        return min + (random.nextFloat() * (max - min));
    }

    private static boolean canReplaceAt(WorldGenLevel level, BlockPos pos) {
        return canReplace(level.getBlockState(pos));
    }

    private static boolean canReplace(BlockState state) {
        return state.isAir() || state.canBeReplaced() || state.is(MiscRegistry.CRYSTAL_REPLACEABLE);
    }
}
