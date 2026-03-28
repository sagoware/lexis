package com.lexis.mixin;

import com.lexis.Lexis;
import com.lexis.module.combat.Reach;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class ReachMixin {
    // 1.21.4'te metot adı getEntityInteractionRange oldu
    @Inject(method = "getEntityInteractionRange", at = @At("HEAD"), cancellable = true)
    private void onGetEntityInteractionRange(CallbackInfoReturnable<Double> cir) {
        Reach reachModule = (Reach) Lexis.INSTANCE.getModuleManager().getModuleByName("Reach");
        if (reachModule != null && reachModule.isEnabled()) {
            double range = reachModule.getSettingByName("Range").doubleValue;
            cir.setReturnValue(range);
        }
    }
}