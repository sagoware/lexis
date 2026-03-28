package com.lexis.mixin;

import com.lexis.Lexis;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @ModifyVariable(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), argsOnly = true)
    private Packet<?> onSendPacket(Packet<?> packet) {
        if (Lexis.INSTANCE.getModuleManager().getModuleByName("NoFall").isEnabled()) {
            if (packet instanceof PlayerMoveC2SPacket movePacket) {
                // 1.21.4'te 7 parametre gerekiyor: x, y, z, yaw, pitch, onGround, horizontalCollision
                return new PlayerMoveC2SPacket.Full(
                        movePacket.getX(0), movePacket.getY(0), movePacket.getZ(0),
                        movePacket.getYaw(0), movePacket.getPitch(0),
                        true, // onGround
                        false // horizontalCollision
                );
            }
        }
        return packet;
    }
}