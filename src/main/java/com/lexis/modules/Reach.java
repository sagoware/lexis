package com.lexis.modules;

public class Reach extends Module {
    public static boolean enabled = false;
    public static float reachDistance = 5.0f; // Normali 3.0f'dir

    public Reach() {
        super("Reach");
    }

    @Override
    public void onEnable() { enabled = true; }

    @Override
    public void onDisable() { enabled = false; }
}