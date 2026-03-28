package com.lexis.module.movement;

import com.lexis.module.Category;
import com.lexis.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MOVEMENT, 0);
    }

    @Override
    public void onTick() {
        // 1.21.4 Fix: isHorizontalCollision -> horizontalCollision oldu
        // Ayrıca oyuncunun ileri basıp basmadığını input.movementForward ile kontrol etmek daha sağlıklıdır.
        if (mc.player != null && mc.player.input.movementForward > 0 && !mc.player.horizontalCollision) {
            // Eğer oyuncu acıkmamışsa ve körlük etkisi yoksa (Vanilla kuralları) otomatik sprint atar
            mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.setSprinting(false);
        }
    }
}