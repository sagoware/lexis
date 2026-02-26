package com.lexis.modules;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.client.MinecraftClient;

public class FullBright extends Module {

    // Mixin kullanmıyoruz, o yüzden istersen static yapmayabilirsin ama kalsın.
    public static boolean enabled = false;

    public FullBright() {
        super("FullBright");
    }

    @Override
    public void onEnable() {
        enabled = true;
        // Açıldığı an efekti çakıyoruz
        applyEffect();
    }

    @Override
    public void onDisable() {
        enabled = false;
        // Kapandığı an efekti siliyoruz
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }

    @Override
    public void onTick() {
        // Eğer modül açıksa ve efekt biterse veya süt içilirse geri gelsin
        if (enabled) {
            applyEffect();
        }
    }

    private void applyEffect() {
        MinecraftClient client = MinecraftClient.getInstance();
        // [GÜVENLİK] Oyuncu dünyada değilse işlem yapma (Crash koruması)
        if (client.player != null) {
            // Süre: 1000 tick (sürekli yenilenir), Seviye: 0, Görünür Parçacıklar: false
            client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000, 0, false, false));
        }
    }
}