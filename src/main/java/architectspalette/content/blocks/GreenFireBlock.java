package architectspalette.content.blocks;

import architectspalette.core.registry.MiscRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class GreenFireBlock extends BaseFireBlock {

    //More or less a copy of SoulFireBlock. Just appropriated for its new purpose.
    public GreenFireBlock(Properties properties) {
        //Does three damage because I think it's funny
        super(properties, 3.0F);
    }

    //It's the little things. Pick block on the fire will give you flint and steel to light one. Vanilla doesn't do this.
    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return new ItemStack(Items.FLINT_AND_STEEL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction face, BlockState facingState, LevelAccessor levelAccessor, BlockPos thisPos, BlockPos facingPos) {
        return this.canSurvive(state, levelAccessor, thisPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState stateIn, LevelReader levelReader, BlockPos pos) {
        return canHeGreen(levelReader, pos.below());
    }

    //Soul Fire doesn't need to check for face being sturdy because the only things it works on are full blocks.
    //Green fire can go on slabs and stuff.
    public static boolean canHeGreen(BlockGetter getter, BlockPos pos) {
        BlockState state = getter.getBlockState(pos);
        return state.is(MiscRegistry.GREEN_FIRE_SUPPORTING) && state.isFaceSturdy(getter, pos, Direction.UP);
    }

    @Override
    protected boolean canBurn(BlockState p_49284_) {
        return true;
    }
}
