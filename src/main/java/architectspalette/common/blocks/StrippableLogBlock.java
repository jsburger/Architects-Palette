package architectspalette.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;

public class StrippableLogBlock extends RotatedPillarBlock {

    private final Block stripsTo;

    public StrippableLogBlock(Properties properties, Block stripsTo) {
        super(properties);
        this.stripsTo = stripsTo;
    }

    //Forge hook
    @Nullable
    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
        if (!stack.canPerformAction(toolAction)) return null;
        if (ToolActions.AXE_STRIP.equals(toolAction)) return stripsTo.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        return null;
    }

}
