package com.lexis.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * DOSYA: ExampleMixin.java
 * GÖREV: Oyun başladığında Lexis'in başarıyla yüklendiğini doğrular.
 */
@Mixin(TitleScreen.class) // Ana menü sınıfına sızıyoruz
public class ExampleMixin {

	@Inject(at = @At("HEAD"), method = "init") // init() metodu 1.21.4'te standarttır
	private void onInit(CallbackInfo info) {
		// [VİDEO NOTU: Oyun açıldığında konsola havalı bir logo bastırıyoruz]
		System.out.println("------------------------------------");
		System.out.println("   LEXIS CLIENT 1.21.4 LOADED       ");
		System.out.println("------------------------------------");
	}
}