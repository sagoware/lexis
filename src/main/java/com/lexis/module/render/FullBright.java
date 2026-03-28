package com.lexis.module.render;
import com.lexis.module.Category;
import com.lexis.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FullBright extends Module {
    public FullBright() { super("FullBright", Category.RENDER, 0); }
    @Override
    public void onTick() {
        if (mc.player != null) mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000, 0, false, false));
    }
    @Override
    public void onDisable() { if (mc.player != null) mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION); }
}