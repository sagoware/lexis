package com.lexis.module.movement;

import com.lexis.module.Category;
import com.lexis.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    public NoFall() { super("NoFall", Category.MOVEMENT, 0); }

    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        // Sadece 2 metreden fazla düşüyorsak paketi kandır
        if (mc.player.fallDistance > 2.0f) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, mc.player.horizontalCollision));
            mc.player.fallDistance = 0; // İstemci tarafında resetle ki takılma yapmasın
        }
    }
}