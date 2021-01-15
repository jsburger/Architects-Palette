package architectspalette.common.blocks.abyssaline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class NewAbyssalineBlock extends Block implements IAbyssalineChargeable {
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");
    public static final DirectionProperty CHARGE_SOURCE = DirectionProperty.create("charge_source",
            Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    public NewAbyssalineBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(CHARGED, false).with(CHARGE_SOURCE, Direction.NORTH));
    }
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CHARGED, CHARGE_SOURCE);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(CHARGED) ? AbyssalineHelper.CHARGE_LIGHT : 0;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        AbyssalineHelper.abyssalineTick(state, worldIn, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 1);
        return this.getDefaultState();
//        return getStateWithNeighborCharge(this.getDefaultState(), context.getWorld(), context.getPos());
    }


}