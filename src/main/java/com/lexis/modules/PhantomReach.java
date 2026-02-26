package com.lexis.modules;

import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class PhantomReach extends com.lexis.modules.Module {
    public double reachDistance = 5.5;

    public PhantomReach() {
        super("Phantom Reach");
    }

    @Override
    public void onTick() {
        if (!this.isEnabled() || mc.player == null || mc.interactionManager == null) return;

        // Sol tık basıldığında vuruş kontrolü
        if (mc.options.attackKey.isPressed()) {
            HitResult hit = mc.crosshairTarget;

            if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
                Entity target = ((EntityHitResult) hit).getEntity();

                double dist = mc.player.distanceTo(target);
                // Survival sınırı olan 3.0 blok üzerindeki mesafeleri zorla.
                if (dist > 3.0 && dist <= reachDistance) {
                    mc.interactionManager.attackEntity(mc.player, target);
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}