package architectspalette.content.blocks.abyssaline;

import architectspalette.core.registry.util.IBlockSetBase;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class AbyssalineBlock extends Block implements IAbyssalineChargeable, IBlockSetBase {
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");
    public static final DirectionProperty CHARGE_SOURCE = DirectionProperty.create("charge_source",
            Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    public AbyssalineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(CHARGED, false).setValue(CHARGE_SOURCE, Direction.NORTH));
    }

    //IBlockSetBase method
    public Block getBlockForPart(StoneBlockSet.SetComponent part, BlockBehaviour.Properties properties, Block base) {
        return switch (part) {
            case SLAB -> new AbyssalineSlabBlock(properties);
            case VERTICAL_SLAB -> new AbyssalineVerticalSlabBlock(properties);
            default -> IBlockSetBase.super.getBlockForPart(part, properties, base);
        };
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGED, CHARGE_SOURCE);
    }

    public static int getLightValue(BlockState state) {
        return state.getValue(CHARGED) ? AbyssalineHelper.CHARGE_LIGHT : 0;
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        AbyssalineHelper.abyssalineNeighborUpdate(this, state, worldIn, pos, blockIn, fromPos);
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        AbyssalineHelper.abyssalineTick(state, worldIn, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        context.getLevel().scheduleTick(context.getClickedPos(), this, 1);
//        return this.defaultBlockState();
        return AbyssalineHelper.getStateWithNeighborCharge(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }


}