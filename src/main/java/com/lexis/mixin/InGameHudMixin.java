package com.lexis.mixin;

import com.lexis.Lexis;
import com.lexis.module.render.ScoreboardMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {



    @Inject(method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At("HEAD"), cancellable = true, remap = true)
    private void onRenderScoreboard(DrawContext context, ScoreboardObjective objective, CallbackInfo ci) {
        if (Lexis.INSTANCE == null || Lexis.INSTANCE.getModuleManager() == null) return;

        var module = Lexis.INSTANCE.getModuleManager().getModuleByName("Scoreboard");
        if (module instanceof ScoreboardMod sbMod && sbMod.isEnabled()) {
            ci.cancel();
            sbMod.renderCustomScoreboard(context, objective);
        }
    }
}