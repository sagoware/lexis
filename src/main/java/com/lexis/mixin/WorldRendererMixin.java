package com.lexis.mixin;

import com.lexis.Lexis; // Lexis ana sınıfından moduleManager'a ulaşmak için
import com.lexis.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
                          LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {

        MinecraftClient mc = MinecraftClient.getInstance();
        DrawContext context = new DrawContext(mc, mc.getBufferBuilders().getEntityVertexConsumers());

        // Lexis.INSTANCE.moduleManager.getModules() veya senin ModuleManager yapın nasılsa ona göre çağır
        // Eğer static değilse Lexis içindeki instance üzerinden çekiyoruz
        for (Module m : Lexis.INSTANCE.moduleManager.modules) {
            if (m.isEnabled()) {
                m.onRender3D(context, tickCounter.getTickDelta(false));
            }
        }
    }
}