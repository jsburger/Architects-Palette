package architectspalette.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class SunstoneBlock extends Block {

    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 2);
    public Function<World, Integer> lightSupplier;

    public SunstoneBlock(Properties properties, Function<World, Integer> getLightState) {
        super(properties);
        this.lightSupplier = getLightState;
        this.setDefaultState(this.stateContainer.getBaseState().with(LIGHT, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIGHT);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(LIGHT, this.lightSupplier.apply(context.getWorld()));
    }

    // Replaced with property
//    @Override
//    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
//        return state.get(LIGHT) * 7;
//    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isRemote) {
            Integer lightstate = this.lightSupplier.apply(worldIn);
            if (!lightstate.equals(state.get(LIGHT))) {
                worldIn.setBlockState(pos, state.with(LIGHT, lightstate), 2 | 4);
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facingState.getBlock() instanceof SunstoneBlock) {
            Random rand = worldIn.getRandom();
            if (rand.nextBoolean() && rand.nextBoolean()) {
                worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 8);
            }
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public static Integer sunstoneLight(World world) {
        return getLightFromTime(world, 0);
    }

    public static Integer moonstoneLight(World world) {
        return getLightFromTime(world, 12000L);
    }

    private static Integer getLightFromTime(World world, long offset) {
        MinecraftServer s = world.getServer();
        if (s == null) { return 0; }
        ServerWorld overworld = s.getWorld(World.OVERWORLD);
        if (overworld != null) {
            long time = (overworld.getDayTime() + offset) % 24000;
            if (time >= 13000 && time <= 23000) return 0;
            if (time >= 3000 && time <= 9000) return 2;
            return 1;
        }
        return 0;
    }

    //(Would be) Used in properties
    public static boolean isOpaque(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(LIGHT) == 0;
    }

    //(Would be) Used in properties
    public static boolean isLit(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(LIGHT) != 0;
    }

    //Used in properties
    public static int lightValue(BlockState state) {
        return state.get(LIGHT) * 7;
    }

}
