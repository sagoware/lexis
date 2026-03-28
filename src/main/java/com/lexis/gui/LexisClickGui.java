package com.lexis.gui;

import com.lexis.Lexis;
import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;
import com.lexis.util.ColorUtil;
import com.lexis.util.FontUtil;
import com.lexis.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.awt.Color;

public class LexisClickGui extends Screen {
    private Module selectedModule = null;
    private Module bindingModule = null;
    private String searchText = "";
    private boolean searching = false;

    // Panel Konumu
    private int guiX = 30, guiY = 40;
    private boolean dragging = false;
    private int dragX, dragY;

    // Ayar Sürükleme
    private Setting draggingSlider = null;

    public LexisClickGui() { super(Text.literal("Lexis ClickGUI")); }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int themeColor = ColorUtil.getThemeColor(0);

        // Arka Plan Karartma
        context.fill(0, 0, this.width, this.height, new Color(0, 0, 0, 120).getRGB());

        // --- ARAMA ÇUBUĞU ---
        int sx = this.width / 2 - 80, sy = 10;
        RenderUtil.drawSmoothRect(context, sx, sy, 160, 20, 0xFF0A0A0A);
        String sDisp = searchText.isEmpty() ? (searching ? "_" : "Ara...") : searchText + (searching ? "_" : "");
        FontUtil.drawText(context, sDisp, sx + 10, sy + 6, searchText.isEmpty() ? 0xFF777777 : -1);

        // Panel Sürükleme
        if (dragging) {
            guiX = mouseX - dragX;
            guiY = mouseY - dragY;
        }

        // --- KATEGORİLER VE MODÜLLER ---
        int xOff = guiX;
        for (Category c : Category.values()) {
            int curY = guiY;

            // Kategori Başlığı (Yumuşak Köşe)
            RenderUtil.drawSmoothRect(context, xOff, curY, 110, 18, 0xFF151515);
            context.fill(xOff + 5, curY + 16, xOff + 105, curY + 17, themeColor);
            FontUtil.drawText(context, c.name(), xOff + 10, curY + 5, -1);
            curY += 22;

            for (Module m : Lexis.INSTANCE.getModuleManager().getModulesByCategory(c)) {
                // Arama Filtresi
                if (!searchText.isEmpty() && !m.getName().toLowerCase().contains(searchText.toLowerCase())) continue;

                boolean hover = RenderUtil.isHovered(mouseX, mouseY, xOff, curY, 110, 16);
                int bg = m.isEnabled() ? ColorUtil.applyAlpha(themeColor, 160) : (hover ? 0xFF252525 : 0xFF0A0A0A);

                RenderUtil.drawSmoothRect(context, xOff, curY, 110, 16, bg);

                // Keybind Modu Kontrolü
                String label = (bindingModule == m) ? "> ? <" : m.getName();
                FontUtil.drawText(context, label, xOff + 8, curY + 4, -1);

                curY += 18;
            }
            xOff += 120;
        }

        // --- SAĞ TARAF: AYARLAR PANELİ ---
        if (selectedModule != null) {
            int sX = this.width - 150, sY = 40, sW = 135;
            RenderUtil.drawSmoothOutline(context, sX - 1, sY - 1, sW + 2, 252, themeColor);
            RenderUtil.drawSmoothRect(context, sX, sY, sW, 250, 0xF0050505);

            FontUtil.drawText(context, selectedModule.getName(), sX + 10, sY + 8, themeColor);
            int curY = sY + 25;

            for (Setting s : selectedModule.settings) {
                FontUtil.drawText(context, s.name, sX + 10, curY, 0xFFCCCCCC);

                if (s.min != s.max) { // SLIDER
                    RenderUtil.drawSmoothRect(context, sX + 10, curY + 12, 115, 4, 0xFF1A1A1A);
                    double perc = (s.doubleValue - s.min) / (s.max - s.min);
                    context.fill(sX + 10, curY + 12, sX + 10 + (int)(perc * 115), curY + 16, themeColor);

                    String val = String.valueOf(s.doubleValue);
                    FontUtil.drawText(context, val, sX + sW - FontUtil.getStringWidth(val) - 10, curY, -1);

                    if (draggingSlider == s) {
                        double v = ((mouseX - (sX + 10)) / 115.0) * (s.max - s.min) + s.min;
                        s.doubleValue = Math.round(Math.max(s.min, Math.min(s.max, v)) * 10.0) / 10.0;
                    }
                    curY += 28;
                } else if (s.modes != null) { // MODE
                    RenderUtil.drawSmoothRect(context, sX + 10, curY + 10, 115, 12, 0xFF121212);
                    FontUtil.drawText(context, s.modeValue, sX + 15, curY + 12, themeColor);
                    curY += 30;
                } else { // CHECKBOX
                    int bCol = s.booleanValue ? themeColor : 0xFF444444;
                    RenderUtil.drawSmoothRect(context, sX + sW - 25, curY, 15, 8, bCol);
                    curY += 18;
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Arama Çubuğu Tıklama
        if (RenderUtil.isHovered(mouseX, mouseY, this.width / 2 - 80, 10, 160, 20)) {
            searching = true;
            return true;
        }

        // Panel Sürükleme
        if (RenderUtil.isHovered(mouseX, mouseY, guiX, guiY, 110, 18) && button == 0) {
            dragging = true;
            dragX = (int)mouseX - guiX;
            dragY = (int)mouseY - guiY;
            return true;
        }

        // Modül Etkileşimi
        int xO = guiX;
        for (Category c : Category.values()) {
            int yO = guiY + 22;
            for (Module m : Lexis.INSTANCE.getModuleManager().getModulesByCategory(c)) {
                if (RenderUtil.isHovered(mouseX, mouseY, xO, yO, 110, 16)) {
                    if (button == 0) m.toggle(); // Sol Tık: Aç/Kapat
                    else if (button == 1) selectedModule = (selectedModule == m ? null : m); // Sağ Tık: Ayarlar
                    else if (button == 2) bindingModule = m; // Orta Tık: Keybind
                    return true;
                }
                yO += 18;
            }
            xO += 120;
        }

        // Ayar Etkileşimi
        if (selectedModule != null) {
            int sX = this.width - 150, curY = 65;
            for (Setting s : selectedModule.settings) {
                if (s.min != s.max && RenderUtil.isHovered(mouseX, mouseY, sX + 10, curY + 12, 115, 4)) {
                    draggingSlider = s;
                    return true;
                }
                if (s.modes != null && RenderUtil.isHovered(mouseX, mouseY, sX + 10, curY + 10, 115, 12)) {
                    s.cycle(); // Mod değiştirme
                    return true;
                }
                if (s.min == s.max && s.modes == null && RenderUtil.isHovered(mouseX, mouseY, sX + 110, curY, 15, 8)) {
                    if (s.name.equals("Edit Position")) {
                        this.client.setScreen(new EditPosScreen(selectedModule));
                    } else {
                        s.booleanValue = !s.booleanValue;
                    }
                    return true;
                }
                curY += (s.min != s.max) ? 28 : (s.modes != null ? 30 : 18);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Keybind Atama
        if (bindingModule != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE) {
                bindingModule.setKeyCode(0);
            } else {
                bindingModule.setKeyCode(keyCode);
            }
            bindingModule = null;
            return true;
        }

        // Arama Yazma
        if (searching) {
            if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                searching = false;
            } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !searchText.isEmpty()) {
                searchText = searchText.substring(0, searchText.length() - 1);
            }
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (searching) {
            searchText += chr;
            return true;
        }
        return false;
    }

    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) { dragging = false; draggingSlider = null; return false; }
    @Override public boolean shouldPause() { return false; }
}