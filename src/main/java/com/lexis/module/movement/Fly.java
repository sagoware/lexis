package com.lexis.module.movement;

import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class Fly extends Module {
    public Fly() {
        super("Fly", Category.MOVEMENT, 0);
        // HATA ÇÖZÜMÜ: Direkt String dizisi olarak gönderiyoruz (Arrays.asList SİLDİK)
        addSetting(new Setting("Mode", "Creative", "Creative", "Glide", "Motion"));
        addSetting(new Setting("Speed", 1.0, 0.1, 5.0));
    }

    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        String mode = getSettingByName("Mode").modeValue;
        double speed = getSettingByName("Speed").doubleValue;

        switch (mode) {
            case "Creative":
                mc.player.getAbilities().flying = true;
                break;
            case "Glide":
                // HATA ÇÖZÜMÜ: isFalling() yerine fallDistance
                if (mc.player.fallDistance > 0) {
                    mc.player.setVelocity(mc.player.getVelocity().x, -0.05, mc.player.getVelocity().z);
                }
                break;
            case "Motion":
                mc.player.getAbilities().flying = false;
                double y = 0;
                if (mc.options.jumpKey.isPressed()) y = speed;
                else if (mc.options.sneakKey.isPressed()) y = -speed;

                // HATA ÇÖZÜMÜ: movementForward ve movementSideways kullanımı
                Vec3d forward = Vec3d.fromPolar(0, mc.player.getYaw()).multiply(speed);
                if (mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0) {
                    mc.player.setVelocity(forward.x, y, forward.z);
                } else {
                    mc.player.setVelocity(0, y, 0);
                }
                break;
        }
    }
}