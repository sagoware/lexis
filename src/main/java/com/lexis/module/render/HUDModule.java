package com.lexis.module.render;

import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;
import com.lexis.util.ColorUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class HUDModule extends Module {

    public HUDModule() {
        // İsim mutlaka "HUD" olmalı, Mixinler bu ismi arıyor
        super("HUD", Category.RENDER, 0);

        addSetting(new Setting("Edit Position", false));
        addSetting(new Setting("Rainbow", false));
        addSetting(new Setting("Red", 0, 0, 255));
        addSetting(new Setting("Green", 170, 0, 255));
        addSetting(new Setting("Blue", 255, 0, 255));
        addSetting(new Setting("Align Right", true));

        // Başlangıçta kapalıysa aç
        if (!this.isEnabled()) {
            this.toggle();
        }
    }

    @Override
    public void onTick() {
        Setting rainbowSet = getSettingByName("Rainbow");
        if (rainbowSet != null) ColorUtil.useRainbow = rainbowSet.booleanValue;

        if (!ColorUtil.useRainbow) {
            try {
                int r = (int) getSettingByName("Red").doubleValue;
                int g = (int) getSettingByName("Green").doubleValue;
                int b = (int) getSettingByName("Blue").doubleValue;
                ColorUtil.clientColor = new Color(r, g, b).getRGB();
            } catch (Exception ignored) {}
        }
    }
}