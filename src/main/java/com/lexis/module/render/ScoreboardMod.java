package com.lexis.module.render;

import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;
import com.lexis.util.ColorUtil;
import com.lexis.util.FontUtil;
import com.lexis.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardMod extends Module {
    public ScoreboardMod() {
        super("Scoreboard", Category.RENDER, 0);
        // EDIT POS BURADA - isBoolean() true dönecek şekilde ayarlandı
        addSetting(new Setting("Edit Position", false));
        addSetting(new Setting("X Pos", 10, 0, 2000));
        addSetting(new Setting("Y Pos", 100, 0, 2000));
        addSetting(new Setting("Hide Numbers", true));
    }

    public void renderCustomScoreboard(DrawContext context, ScoreboardObjective objective) {
        if (objective == null) return;

        int x = (int) getSettingByName("X Pos").doubleValue;
        int y = (int) getSettingByName("Y Pos").doubleValue;
        int themeColor = ColorUtil.getThemeColor(0);

        // 1.21.4 KESİN VERİ ÇEKME YÖNTEMİ
        var scoreboard = objective.getScoreboard();
        List<ScoreboardEntry> scores = scoreboard.getScoreboardEntries(objective)
                .stream()
                .sorted(Comparator.comparingInt(ScoreboardEntry::value).reversed())
                .limit(15)
                .collect(Collectors.toList());

        String title = objective.getDisplayName().getString();
        int maxWidth = FontUtil.getStringWidth(title);

        // Veri var mı kontrolü ve genişlik hesaplama
        for (ScoreboardEntry entry : scores) {
            // entry.name() 1.21.4 için en garanti isim çekme yoludur
            String name = entry.name() != null ? entry.name().getString() : entry.owner();
            int w = FontUtil.getStringWidth(name);
            if (w > maxWidth) maxWidth = w;
        }

        int width = maxWidth + 25;
        int height = (scores.size() + 1) * 11 + 6;

        RenderUtil.drawSmoothOutline(context, x - 1, y - 1, width + 2, height + 2, themeColor);
        RenderUtil.drawSmoothRect(context, x, y, width, height, 0xEE050505);
        FontUtil.drawText(context, title, x + (width / 2) - (FontUtil.getStringWidth(title) / 2), y + 3, themeColor);

        int i = 0;
        for (ScoreboardEntry entry : scores) {
            int rowY = y + 15 + (i * 11);
            // İSİM ÇEKME BURADA DÜZELTİLDİ
            String name = entry.name() != null ? entry.name().getString() : entry.owner();

            FontUtil.drawText(context, name, x + 5, rowY, -1);
            if (!getSettingByName("Hide Numbers").booleanValue) {
                String val = String.valueOf(entry.value());
                FontUtil.drawText(context, val, x + width - FontUtil.getStringWidth(val) - 5, rowY, themeColor);
            }
            i++;
        }
    }
}