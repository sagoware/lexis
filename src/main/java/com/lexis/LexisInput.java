package com.lexis;

import com.lexis.gui.LexisClickGui;
import com.lexis.modules.Module;
import net.minecraft.client.MinecraftClient;

public class LexisInput {
    public static void handleInput(int key, int action) {
        if (action == 1) { // Basılma anı
            // Sağ Shift (GLFW kodu: 344)
            if (key == 344) {
                MinecraftClient.getInstance().setScreen(new LexisClickGui());
            }

            // Atanmış tuşları kontrol et
            for (Module m : LexisMod.modules) {
                if (m.getKey() == key) {
                    m.toggle();
                }
            }
        }
    }
}