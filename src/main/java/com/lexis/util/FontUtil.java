package com.lexis.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FontUtil {
    // Resources içindeki fontu tanımlıyoruz
    public static final Identifier LEXIS_FONT = Identifier.of("lexis", "default");

    public static void drawText(DrawContext context, String text, float x, float y, int color) {
        // Metne özel font stilini uyguluyoruz
        context.drawText(
                MinecraftClient.getInstance().textRenderer,
                Text.literal(text).setStyle(Style.EMPTY.withFont(LEXIS_FONT)),
                (int)x, (int)y,
                color,
                true // Shadow (Gölge)
        );
    }

    public static int getStringWidth(String text) {
        return MinecraftClient.getInstance().textRenderer.getWidth(
                Text.literal(text).setStyle(Style.EMPTY.withFont(LEXIS_FONT))
        );
    }
}