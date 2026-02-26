package com.lexis;

import com.lexis.modules.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import java.util.ArrayList;
import java.util.List;

public class LexisMod implements ClientModInitializer {
	// [VİDEO NOTU: Java'nın kendi Module sınıfıyla karışmaması için tam yolunu belirttik.]
	public static List<com.lexis.modules.Module> modules = new ArrayList<>();

	@Override
	public void onInitializeClient() {
		modules.add(new PhantomReach());
		modules.add(new Reach());
		modules.add(new FullBright());
		modules.add(new XRay());

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				for (com.lexis.modules.Module m : modules) {
					m.onTick();
				}
			}
		});
	}
}