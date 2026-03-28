package com.lexis.module;

import com.lexis.module.combat.*;
import com.lexis.module.movement.*;
import com.lexis.module.render.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    public List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new KillAura());
        modules.add(new Reach());
        modules.add(new ScoreboardMod());
        modules.add(new TargetHud());
        modules.add(new FullBright());
        modules.add(new HUDModule());
        modules.add(new ESP());
        modules.add(new Fly());
    }

    public List<Module> getModules() { return modules; }

    public List<Module> getModulesByCategory(Category category) {
        return modules.stream()
                .filter(m -> m.getCategory() == category)
                .collect(Collectors.toList());
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz) return (T) m;
        }
        return null;
    }

    public Module getModuleByName(String name) {
        if (name == null) return null;
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name.trim())) return m;
        }
        // CRASH ENGELLEYİCİ: Modül bulunamazsa null dönmek yerine "boş" bir modül dönüyoruz
        // Böylece .isEnabled() çağrısı NullPointerException vermez
        return new Module("Safety", Category.RENDER, 0);
    }
}