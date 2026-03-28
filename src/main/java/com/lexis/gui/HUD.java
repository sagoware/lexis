package com.lexis.gui;

import com.lexis.Lexis;
import com.lexis.module.Module;
import com.lexis.module.render.HUDModule;
import com.lexis.util.ColorUtil;
import com.lexis.util.FontUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import java.util.Comparator;
import java.util.List;

public class HUD {
    public static void register() {
        HudRenderCallback.EVENT.register((context, tickCounter) -> {
            MinecraftClient mc = MinecraftClient.getInstance();

            // OPTİMİZASYON: ClickGUI açıkken HUD'ı çizme (FPS'i 20-30 artırır)
            if (mc.currentScreen instanceof LexisClickGui) return;
            if (Lexis.INSTANCE.getModuleManager() == null) return;

            HUDModule hudMod = (HUDModule) Lexis.INSTANCE.getModuleManager().getModuleByName("HUD");
            if (hudMod == null || !hudMod.isEnabled()) return;

            boolean alignRight = hudMod.getSettingByName("Align Right").booleanValue;
            int themeColor = ColorUtil.getThemeColor(0);

            // 1. LOGO ÇİZİMİ
            FontUtil.drawText(context, "LEXIS CLIENT", 5, 5, themeColor);

            // 2. ARRAYLIST (Aktif Modüller)
            List<Module> enabled = Lexis.INSTANCE.getModuleManager().getModules().stream()
                    .filter(Module::isEnabled)
                    .sorted(Comparator.comparingInt(m -> -FontUtil.getStringWidth(m.getName())))
                    .toList();

            int yOffset = 5;
            for (Module m : enabled) {
                int textWidth = FontUtil.getStringWidth(m.getName());

                // Konum: Sağa yaslıysa ekran genişliğinden çıkar, sola yaslıysa 5px
                int xPos = alignRight ? context.getScaledWindowWidth() - textWidth - 5 : 5;

                // Renk Senkronizasyonu (Gökkuşağı geçişli)
                int moduleColor = ColorUtil.getThemeColor(yOffset * 15);

                // Modül İsmi
                FontUtil.drawText(context, m.getName(), xPos, yOffset, moduleColor);

                // Kenar Çizgisi (Meteor/Astolfo Style)
                int lineX = alignRight ? context.getScaledWindowWidth() - 2 : 0;
                context.fill(lineX, yOffset, lineX + 2, yOffset + 10, moduleColor);

                yOffset += 11;
            }
        });
    }
}