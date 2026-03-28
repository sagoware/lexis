package com.lexis.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;

public class RenderUtil {

    public static void draw3DBox(DrawContext context, Box box, int color, float lineWidth, boolean fill) {
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        VertexConsumer consumer = immediate.getBuffer(RenderLayer.getLines());

        // Alt Kare
        v(consumer, matrix, (float)box.minX, (float)box.minY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.minY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.minY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.minY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.minY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.minY, (float)box.minZ, r, g, b, a);

        // Üst Kare
        v(consumer, matrix, (float)box.minX, (float)box.maxY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.maxY, (float)box.minZ, r, g, b, a);

        // Dikey Kenarlar
        v(consumer, matrix, (float)box.minX, (float)box.minY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.maxY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.minY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.minY, (float)box.maxZ, r, g, b, a);
        v(consumer, matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ, r, g, b, a);

        immediate.draw();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    private static void v(VertexConsumer c, Matrix4f m, float x, float y, float z, float r, float g, float b, float a) {
        c.vertex(m, x, y, z).color(r, g, b, a).normal(0, 1, 0);
    }

    public static void drawSmoothRect(DrawContext context, int x, int y, int width, int height, int color) {
        context.fill(x, y, x + width, y + height, color);
    }

    public static void drawSmoothOutline(DrawContext context, int x, int y, int width, int height, int color) {
        context.drawBorder(x, y, width, height, color);
    }

    public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}