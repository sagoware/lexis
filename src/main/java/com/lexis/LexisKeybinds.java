package com.lexis;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class LexisKeybinds {

    public static KeyBinding toggleReach;
    public static KeyBinding toggleFullBright; // Yeni tuşumuz

    public static void register() {
        toggleReach = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lexis.reach",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.lexis"
        ));

        // [VİDEO NOTU: G tuşu Gece Görüşü (Gamma) için atanıyor.]
        toggleFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lexis.fullbright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.lexis"
        ));
    }
}