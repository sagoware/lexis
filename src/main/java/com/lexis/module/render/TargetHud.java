package com.lexis.module.render;

import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;
import com.lexis.util.ColorUtil;
import com.lexis.util.FontUtil;
import com.lexis.util.RenderUtil;
import com.lexis.gui.EditPosScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;

public class TargetHud extends Module {
    public LivingEntity target;

    public TargetHud() {
        super("TargetHUD", Category.RENDER, 0);
        addSetting(new Setting("Edit Position", false)); // EDIT POS BURADA
        addSetting(new Setting("X Pos", 400, 0, 2000));
        addSetting(new Setting("Y Pos", 300, 0, 2000));
    }

    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.targetedEntity instanceof LivingEntity living) target = living;
        if (target != null && (target.isDead() || target.distanceTo(mc.player) > 15)) target = null;
    }

    public void onRender(DrawContext context) {
        MinecraftClient mc = MinecraftClient.getInstance();

        // Düzenleme modunda boş görünmemesi için
        LivingEntity rt = target;
        if (rt == null && mc.currentScreen instanceof EditPosScreen) rt = mc.player;

        if (rt == null) return;

        int x = (int) getSettingByName("X Pos").doubleValue;
        int y = (int) getSettingByName("Y Pos").doubleValue;
        int themeColor = ColorUtil.getThemeColor(0);

        RenderUtil.drawSmoothOutline(context, x - 1, y - 1, 142, 42, themeColor);
        RenderUtil.drawSmoothRect(context, x, y, 140, 40, 0xEE050505);

        FontUtil.drawText(context, rt.getName().getString(), x + 5, y + 5, -1);
        float hpPerc = Math.min(1, rt.getHealth() / rt.getMaxHealth());
        RenderUtil.drawSmoothRect(context, x + 5, y + 18, 130, 6, 0xFF151515);
        RenderUtil.drawSmoothRect(context, x + 5, y + 18, (int)(130 * hpPerc), 6, themeColor);
        FontUtil.drawText(context, (int)rt.getHealth() + " HP", x + 5, y + 28, 0xFFBBBBBB);
    }
}