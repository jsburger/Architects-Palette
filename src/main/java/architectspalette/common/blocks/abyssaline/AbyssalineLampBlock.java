package architectspalette.common.blocks.abyssaline;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGED;
import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGE_SOURCE;

public class AbyssalineLampBlock extends RotatedPillarBlock implements IAbyssalineChargeable {

    public AbyssalineLampBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(CHARGE_SOURCE, Direction.NORTH).with(CHARGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS, CHARGED, CHARGE_SOURCE);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return this.isCharged(state) ? 16 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 1);
        return super.getStateForPlacement(context);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        AbyssalineHelper.abyssalineTick(state, worldIn, pos);
    }

}
