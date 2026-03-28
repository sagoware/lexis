package com.lexis.module.combat;

import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class KillAura extends Module {
    public KillAura() {
        super("KillAura", Category.COMBAT, GLFW.GLFW_KEY_R);
        addSetting(new Setting("Range", 4.0, 3.0, 6.0));
        addSetting(new Setting("Players", true));
        addSetting(new Setting("Mobs", false));
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        double range = getSettingByName("Range").doubleValue;
        boolean targetPlayers = getSettingByName("Players").booleanValue;
        boolean targetMobs = getSettingByName("Mobs").booleanValue;

        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player || !entity.isAlive()) continue;
            if (mc.player.distanceTo(entity) > range) continue;

            if ((entity instanceof PlayerEntity && targetPlayers) || (entity instanceof HostileEntity && targetMobs)) {
                // Vuruş Yap
                if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                    mc.interactionManager.attackEntity(mc.player, entity);
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
                break; // Her tick'te sadece birine vur (Optimizasyon)
            }
        }
    }
}