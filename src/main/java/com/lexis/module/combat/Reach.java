package com.lexis.module.combat;

import com.lexis.module.Category;
import com.lexis.module.Module;
import com.lexis.settings.Setting;

public class Reach extends Module {
    public Reach() {
        super("Reach", Category.COMBAT, 0);
        addSetting(new Setting("Range", 4.5, 3.0, 6.0));
    }
}