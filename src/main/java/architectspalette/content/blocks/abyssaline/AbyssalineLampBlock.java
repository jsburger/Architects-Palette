package architectspalette.content.blocks.abyssaline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;


import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGED;
import static architectspalette.content.blocks.abyssaline.AbyssalineBlock.CHARGE_SOURCE;

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
        return AbyssalineHelper.getStateWithNeighborCharge(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
    }

	@Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        AbyssalineHelper.abyssalineTick(state, worldIn, pos);
    }

}
