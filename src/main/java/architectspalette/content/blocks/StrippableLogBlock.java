package architectspalette.content.blocks;

import net.minecraft.world.item.context.UseOnContext;
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

    @Override
    @Nullable
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        var stack = context.getItemInHand();
        if (!stack.canPerformAction(toolAction)) return null;
        if (ToolActions.AXE_STRIP.equals(toolAction)) return stripsTo.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

}
