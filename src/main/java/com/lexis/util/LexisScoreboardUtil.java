package com.lexis.util;

import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.text.Text;

public class LexisScoreboardUtil {
    public static String getPlayerName(ScoreboardEntry entry) {
        if (entry == null) return "Bilinmiyor";

        // 1.21.4'te isim önceliği: DisplayName > Name > Owner
        if (entry.name() != null) return entry.name().getString();
        if (entry.owner() != null) return entry.owner();

        return "Bilinmiyor";
    }
}