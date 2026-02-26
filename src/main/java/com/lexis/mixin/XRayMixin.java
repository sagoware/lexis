package com.lexis.mixin;

import com.lexis.modules.XRay;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class XRayMixin {

    @Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
    private void onIsSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (XRay.enabled) {
            // TARGET_BLOCKS listesini doğrudan XRay modülünden çekiyoruz
            boolean isTarget = XRay.TARGET_BLOCKS.contains(state.getBlock());
            cir.setReturnValue(!isTarget);
        }
    }

    @Inject(method = "getAmbientOcclusionLightLevel", at = @At("HEAD"), cancellable = true)
    private void onGetAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (XRay.enabled) {
            cir.setReturnValue(1.0f);
        }
    }

    // World ve Pos parametrelerini sildik, sadece State ve Cir bıraktık (1.21.4 standardı)
    @Inject(method = "getOpacity", at = @At("HEAD"), cancellable = true)
    private void onGetOpacity(BlockState state, CallbackInfoReturnable<Integer> cir) {
        if (XRay.enabled) {
            boolean isTarget = XRay.TARGET_BLOCKS.contains(state.getBlock());
            cir.setReturnValue(isTarget ? 15 : 0);
        }
    }
}