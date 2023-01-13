package architectspalette.content.blocks;

import architectspalette.core.config.APConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Function;

@SuppressWarnings("deprecation")
public class SunstoneBlock extends Block {

    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 2);
    public Function<Level, Integer> lightSupplier;

    public SunstoneBlock(Properties properties, Function<Level, Integer> getLightState) {
        super(properties);
        this.lightSupplier = getLightState;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LIGHT, this.lightSupplier.apply(context.getLevel()));
    }

    // Replaced with property
//    @Override
//    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
//        return state.get(LIGHT) * 7;
//    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (!worldIn.isClientSide) {
            Integer lightstate = this.lightSupplier.apply(worldIn);
            if (!lightstate.equals(state.getValue(LIGHT))) {
                worldIn.setBlock(pos, state.setValue(LIGHT, lightstate), 2 | 4);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facingState.getBlock() instanceof SunstoneBlock) {
            //Default 35%
            Double chance = APConfig.SUNSTONE_SPREAD_CHANCE.get();
            if (chance > 0) {
                RandomSource rand = worldIn.getRandom();
                if (rand.nextDouble() <= chance) {
                    worldIn.scheduleTick(currentPos, this, (int) (2 + Math.floor(rand.nextDouble() * 6)));
                }
            }
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public static Integer sunstoneLight(Level world) {
        return getLightFromTime(world, 0);
    }

    public static Integer moonstoneLight(Level world) {
        return getLightFromTime(world, 12000L);
    }

    private static Integer getLightFromTime(Level world, long offset) {
        MinecraftServer s = world.getServer();
        if (s == null) { return 0; }
        ServerLevel overworld = s.getLevel(Level.OVERWORLD);
        if (overworld != null) {
            long time = (overworld.getDayTime() + offset) % 24000;
            if (time >= 12500 && time <= 23500) return 0;
            if (time >= 3500 && time <= 8500) return 2;
            return 1;
        }
        return 0;
    }

    //(Would be) Used in properties
    public static boolean isOpaque(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getValue(LIGHT) == 0;
    }

    //(Would be) Used in properties
    public static boolean isLit(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getValue(LIGHT) != 0;
    }

    //Used in properties
    public static int lightValue(BlockState state) {
        return state.getValue(LIGHT) * 7;
    }

}
