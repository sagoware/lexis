package com.lexis;

import com.lexis.gui.HUD;
import com.lexis.gui.LexisClickGui;
import com.lexis.module.Module;
import com.lexis.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

public class Lexis implements ClientModInitializer {
	public static Lexis INSTANCE;
	public ModuleManager moduleManager;
	private KeyBinding guiKeyBinding;
	private final Set<Integer> pressedKeys = new HashSet<>();

	@Override
	public void onInitializeClient() {
		INSTANCE = this;
		this.moduleManager = new ModuleManager();
		HUD.register();

		guiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.lexis.gui",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_RIGHT_SHIFT,
				"Lexis"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			while (guiKeyBinding.wasPressed()) {
				client.setScreen(new LexisClickGui());
			}

			for (Module m : moduleManager.getModules()) {
				int key = m.getKeyCode();
				if (key != 0 && client.currentScreen == null) {
					boolean isDown = InputUtil.isKeyPressed(client.getWindow().getHandle(), key);
					if (isDown && !pressedKeys.contains(key)) {
						m.toggle();
						pressedKeys.add(key);
					} else if (!isDown) {
						pressedKeys.remove(key);
					}
				}
				if (m.isEnabled()) m.onTick();
			}
		});
	}

	public ModuleManager getModuleManager() { return moduleManager; }
}