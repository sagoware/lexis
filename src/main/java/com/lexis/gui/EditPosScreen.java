package com.lexis.gui;

import com.lexis.module.Module;
import com.lexis.settings.Setting;
import com.lexis.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class EditPosScreen extends Screen {
    private final Module module;
    private boolean dragging = false;

    public EditPosScreen(Module module) {
        super(Text.literal("Editing Position"));
        this.module = module;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Arka planı hafif karart ki neyi sürüklediğini gör
        context.fill(0, 0, this.width, this.height, 0x44000000);
        context.drawCenteredTextWithShadow(this.textRenderer, "Sürükle ve Yerleştir - Çıkmak için ESC", this.width / 2, 10, -1);

        Setting xSet = module.getSettingByName("X Pos");
        Setting ySet = module.getSettingByName("Y Pos");

        if (dragging) {
            xSet.doubleValue = mouseX - 50;
            ySet.doubleValue = mouseY - 10;
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) dragging = true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) dragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}