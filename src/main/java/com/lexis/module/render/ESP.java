package com.lexis.module.render;

import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;
import com.lexis.util.ColorUtil;
import com.lexis.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ESP extends Module {

    public ESP() {
        super("ESP", Category.RENDER, 0);
        addSetting(new Setting("Line Width", 1.5, 0.5, 5.0));
    }

    @Override
    public void onRender3D(DrawContext context, float partialTicks) {
        if (!this.isEnabled()) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null || mc.player == null) return;

        Vec3d cameraPos = mc.getEntityRenderDispatcher().camera.getPos();

        // Ayarı güvenli bir şekilde çekiyoruz
        Setting lineSet = getSettingByName("Line Width");
        float lineWidth = (lineSet != null) ? (float) lineSet.doubleValue : 1.5f;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof PlayerEntity) || entity == mc.player) continue;

            // Interpolated pozisyon hesaplama (Titremeyi önler)
            double x = entity.prevX + (entity.getX() - entity.prevX) * partialTicks - cameraPos.x;
            double y = entity.prevY + (entity.getY() - entity.prevY) * partialTicks - cameraPos.y;
            double z = entity.prevZ + (entity.getZ() - entity.prevZ) * partialTicks - cameraPos.z;

            Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ()).offset(x, y, z);
            int color = ColorUtil.getThemeColor(0);

            // RenderUtil üzerinden 3D kutuyu çiziyoruz
            RenderUtil.draw3DBox(context, box, color, lineWidth, false);
        }
    }
}