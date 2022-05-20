package architectspalette.core.mixin;

import architectspalette.content.blocks.GreenFireBlock;
import architectspalette.core.registry.APBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {

    @Inject(method = "getState", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    //arguments for target method, callback info, then captured locals
    private static void architectsPaletteAddGreenFireMixin(BlockGetter p_49246_, BlockPos p_49247_, CallbackInfoReturnable<BlockState> ci, BlockPos below, BlockState belowState) {
        if (GreenFireBlock.canHeGreen(belowState)) {
            ci.setReturnValue(APBlocks.NETHER_BRASS_FIRE.get().defaultBlockState());
        }
    }
}
