package com.lexis.mixin;

import com.lexis.modules.FullBright;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LightmapTextureManager.class, priority = 1000)
public class FullBrightMixin {

    // getBrightness metodu ışık dokusunun parlaklığını belirler.
    // Sodium bu değeri alıp shader'larına gönderir.
    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private static void onGetBrightness(CallbackInfoReturnable<Float> cir) {
        if (FullBright.enabled) {
            // 1.0f normal, 15.0f ise aşırı parlaktır (Zifiri karanlığı yok eder).
            cir.setReturnValue(15.0f);
        }
    }
}