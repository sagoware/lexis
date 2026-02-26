package com.lexis.modules;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import java.util.HashSet;
import java.util.Set;

public class XRay extends Module {
    public static boolean enabled = false;
    public static final Set<Block> TARGET_BLOCKS = new HashSet<>();

    public XRay() {
        super("X-Ray");
        setupBlocks();
    }

    private void setupBlocks() {
        TARGET_BLOCKS.add(Blocks.DIAMOND_ORE);
        TARGET_BLOCKS.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        TARGET_BLOCKS.add(Blocks.GOLD_ORE);
        TARGET_BLOCKS.add(Blocks.DEEPSLATE_GOLD_ORE);
        TARGET_BLOCKS.add(Blocks.ANCIENT_DEBRIS);
        // Diğer madenleri buraya ekle...
    }

    @Override
    public void onEnable() {
        enabled = true;
        safeReload();
    }

    @Override
    public void onDisable() {
        enabled = false;
        safeReload();
    }

    private void safeReload() {
        // [VİDEO NOTU: Crash koruması]
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.worldRenderer != null && client.world != null) {
            client.worldRenderer.reload();
        }
    }
}