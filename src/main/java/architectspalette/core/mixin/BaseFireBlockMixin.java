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

    //Idk if this is the best way to implement a new fire, it was just the most straight forward. I did no research.
    @Inject(method = "getState", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    //arguments for target method, callback info, then captured locals
    private static void architectsPaletteAddGreenFireMixin(BlockGetter getter, BlockPos pos, CallbackInfoReturnable<BlockState> ci, BlockPos below, BlockState belowState) {
        if (GreenFireBlock.canHeGreen(getter, below)) {
            ci.setReturnValue(APBlocks.NETHER_BRASS_FIRE.get().defaultBlockState());
        }
    }
}
