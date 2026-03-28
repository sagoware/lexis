package com.lexis.mixin;

import com.lexis.gui.AltManagerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin extends Screen {
    protected MultiplayerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        // Sağ üst köşe: Genişlik - 105 (buton genişliği 100 + 5 offset), Y: 5
        this.addDrawableChild(ButtonWidget.builder(Text.of("Alt Manager"), button -> {
            this.client.setScreen(new AltManagerScreen(this));
        }).dimensions(this.width - 105, 5, 100, 20).build());
    }
}