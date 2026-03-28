package com.lexis.util;

import java.awt.Color;

public class ColorUtil {
    public static int clientColor = new Color(0, 170, 255).getRGB();
    public static boolean useRainbow = false;

    public static int getThemeColor(int offset) {
        if (useRainbow) return getRainbow(4.0f, offset);
        return clientColor;
    }

    public static int getRainbow(float speed, int offset) {
        float hue = (System.currentTimeMillis() + offset) % (int)(speed * 1000) / (float)(speed * 1000);
        return Color.HSBtoRGB(hue, 1.0f, 1.0f);
    }

    // ESP'deki ColorUtil.toRGBA hatasını çözen metot
    public static int toRGBA(int color, int alpha) {
        Color c = new Color(color);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha).getRGB();
    }

    // Senin eski metodun (toRGBA ile aynı işi yapar, istersen bunu da kullanabilirsin)
    public static int applyAlpha(int color, int alpha) {
        Color c = new Color(color);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha).getRGB();
    }

    // ESP'deki getHealthColor hatasını çözen metot
    public static int getHealthColor(float percentage) {
        // Can %100 ise yeşil (0.33), %0 ise kırmızı (0) döner
        return Color.HSBtoRGB(percentage / 3f, 1.0f, 1.0f);
    }
}