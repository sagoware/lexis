package com.lexis.modules;

import net.minecraft.client.MinecraftClient;

public class Module {
    private String name;
    private int key;
    private boolean enabled;
    // mc tanımını bu şekilde değiştir:
    protected static final net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();

    public Module(String name) {
        this.name = name;
        this.key = 0;
        this.enabled = false;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    public String getName() { return name; }
    public boolean isEnabled() { return enabled; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
}