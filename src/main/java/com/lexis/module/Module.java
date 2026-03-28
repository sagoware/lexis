package com.lexis.module;

import com.lexis.settings.Setting;
import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;

public class Module {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private String name;
    private Category category;
    private int keyCode;
    private boolean enabled;
    public List<Setting> settings = new ArrayList<>();

    public Setting getSettingByName(String name) {
        return settings.stream().filter(s -> s.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    // ESP ve diğer render modüllerinin hata vermemesi için ana metot
    public void onRender3D(net.minecraft.client.gui.DrawContext context, float partialTicks) {
        // İçi boş kalacak, ESP gibi alt sınıflar bunu override edip dolduracak.
    }

    public Module(String name, Category category, int keyCode) {
        this.name = name;
        this.category = category;
        this.keyCode = keyCode;
    }

    public void addSetting(Setting s) { this.settings.add(s); }
    public void onTick() {}
    public void onEnable() {}
    public void onDisable() {}

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getKeyCode() { return keyCode; }
    public void setKeyCode(int keyCode) { this.keyCode = keyCode; }
    public boolean isEnabled() { return enabled; }
}