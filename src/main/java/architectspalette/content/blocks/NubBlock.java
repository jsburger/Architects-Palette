package architectspalette.content.blocks;

import architectspalette.content.blocks.util.APWeatheringCopper;
import architectspalette.content.blocks.util.WaterloggableDirectionalBlock;
import architectspalette.core.util.ShapeRotator;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class NubBlock extends WaterloggableDirectionalBlock {

    private static final Map<Direction, VoxelShape> SHAPES;

    static {
        VoxelShape north = Block.box(3, 3, 0, 13, 13, 5);
        VoxelShape up = Block.box(3, 11, 3, 13, 16, 13);

        ImmutableMap.Builder<Direction, VoxelShape> builder = new ImmutableMap.Builder<>();

        for (Direction dir : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST}) {
            builder.put(dir, ShapeRotator.rotateShapeHorizontal(north, Direction.NORTH, dir));
        }
        builder.put(Direction.UP, up);
        builder.put(Direction.DOWN, ShapeRotator.flipShapeVertical(up));
        SHAPES = builder.build();
    }

    public NubBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }


    //Copper nubs
    public static class CopperNubBlock extends NubBlock implements APWeatheringCopper {
        private final WeatheringCopper.WeatherState weatherState;

        public CopperNubBlock(Properties properties, WeatheringCopper.WeatherState weatherState) {
            super(properties);
            this.weatherState = weatherState;
        }

        public void randomTick(BlockState stateIn, ServerLevel worldIn, BlockPos pos, RandomSource random) {
            this.onRandomTick(stateIn, worldIn, pos, random);
        }

        public boolean isRandomlyTicking(BlockState state) {
            return APWeatheringCopper.getNext(state.getBlock()).isPresent();
        }

        public WeatheringCopper.WeatherState getAge() {
            return this.weatherState;
        }

        @Nullable
        @Override
        public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
            BlockState a = APWeatheringCopper.getToolModifiedState(toolAction, state);
            if (a != null) return a;
            return super.getToolModifiedState(state, context, toolAction, simulate);
        }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
            return APWeatheringCopper.onUse(state, level, pos, player, hand);
        }
    }

}
