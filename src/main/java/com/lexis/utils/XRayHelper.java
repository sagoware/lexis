package com.lexis.utils;

import com.lexis.modules.XRay;
import net.minecraft.block.Block;

public class XRayHelper {
    public static int getOpacityValue(Block block) {
        if (!XRay.enabled) return -1;
        // Madenler opak kalsın (15), diğer her şey (taş vb.) ışık geçirsin (0)
        return XRay.TARGET_BLOCKS.contains(block) ? 15 : 0;
    }
}