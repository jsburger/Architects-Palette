package architectspalette.common.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.Random;

import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGED;
import static architectspalette.common.blocks.abyssaline.NewAbyssalineBlock.CHARGE_SOURCE;

public class AbyssalineLampBlock extends RotatedPillarBlock implements IAbyssalineChargeable {

    public AbyssalineLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CHARGE_SOURCE, Direction.NORTH).setValue(CHARGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, CHARGED, CHARGE_SOURCE);
    }

    public static int getLightValue(BlockState state) {
        return state.getValue(CHARGED) ? 16 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getLevel().scheduleTick(context.getClickedPos(), this, 1);
        return super.getStateForPlacement(context);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
    }

	@Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        AbyssalineHelper.abyssalineTick(state, worldIn, pos);
    }

}
