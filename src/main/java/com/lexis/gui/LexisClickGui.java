package com.lexis.gui;

import com.lexis.LexisMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class LexisClickGui extends Screen {
    private com.lexis.modules.Module bindingModule = null;

    public LexisClickGui() {
        super(Text.literal("Lexis Menu"));
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // Blur yerine sadece hafif bir karartma (Sodium uyumlu)
        context.fill(0, 0, this.width, this.height, 0x90000000);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int y = 50;
        int x = 40;

        context.drawTextWithShadow(this.textRenderer, "§b§lLEXIS CLIENT §f- §71.21.4", x, 30, -1);

        for (com.lexis.modules.Module m : LexisMod.modules) {
            int color = m.isEnabled() ? 0xFF55FF55 : 0xFFFF5555;
            String keyName = m.getKey() == 0 ? "NONE" : String.valueOf((char) m.getKey()).toUpperCase();

            context.fill(x - 5, y - 5, x + 160, y + 15, 0xAA202020);
            String text = m.getName() + " §7[" + (bindingModule == m ? "???" : keyName) + "]";
            context.drawTextWithShadow(this.textRenderer, text, x, y, color);
            y += 22;
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = 50;
        for (com.lexis.modules.Module m : LexisMod.modules) {
            if (mouseX >= 35 && mouseX <= 195 && mouseY >= y - 5 && mouseY <= y + 15) {
                if (button == 0) m.toggle();
                else if (button == 2) bindingModule = m;
                return true;
            }
            y += 22;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (bindingModule != null) {
            bindingModule.setKey(keyCode);
            bindingModule = null;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() { return false; }
}