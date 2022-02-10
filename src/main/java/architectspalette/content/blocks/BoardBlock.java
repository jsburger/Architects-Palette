package architectspalette.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BoardBlock extends Block {

    private static final BooleanProperty ODD_X = BooleanProperty.create("odd_x");
    private static final BooleanProperty ODD_Z = BooleanProperty.create("odd_z");

    public BoardBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any().setValue(ODD_X, false).setValue(ODD_Z, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ODD_X, ODD_Z);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateForPosition(defaultBlockState(), context.getClickedPos());
    }

//    @Override
//    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
//        super.setPlacedBy(worldIn, pos, state, placer, stack);
//        if (!worldIn.isClientSide()) {
//            worldIn.setBlock(pos, state.setValue(ODD, isOddPosition(pos)), 0);
//        }
//    }

    private static BlockState getStateForPosition(BlockState stateIn, BlockPos pos) {
        return stateIn.setValue(ODD_X, isOdd(pos.getX())).setValue(ODD_Z, isOdd(pos.getZ()));
    }

    private static boolean isOdd(int num) {
        return (Math.abs(num) % 2) == 1;
    }


}
