package me.cutebow.crystalanchorcounter.client.util;

import me.cutebow.crystalanchorcounter.client.config.ConfigManager;
import me.cutebow.crystalanchorcounter.client.config.CounterConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

@Environment(EnvType.CLIENT)
public class B {
    public static void drawHud(DrawContext ctx, String left, String right) {
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer tr = mc.textRenderer;
        CounterConfig cfg = ConfigManager.config;
        if (cfg.hudEnabled == null || !cfg.hudEnabled) return;

        String text = switch (cfg.format.toLowerCase()) {
            case "long" -> "Crystals: " + left + " | Anchors: " + right;
            case "pretty" -> "[CPS: " + left + "] [APS: " + right + "]";
            case "chat" -> "<CPS " + left + "> <APS " + right + ">";
            default -> left + " | " + right;
        };

        int w = mc.getWindow().getScaledWidth();
        int h = mc.getWindow().getScaledHeight();
        int x = 0;
        int y = 0;

        switch (cfg.position) {
            case TOP_RIGHT -> {
                x = w - tr.getWidth(text) - 6;
                y = 6;
            }
            case TOP_LEFT -> {
                x = 6;
                y = 6;
            }
            case BOTTOM_LEFT -> {
                x = 6;
                y = h - 20;
            }
            case BOTTOM_RIGHT -> {
                x = w - tr.getWidth(text) - 6;
                y = h - 20;
            }
            case HOTBAR -> {
                x = w / 2 - tr.getWidth(text) / 2;
                int n = 9;
                y = h - 48 - n * 2 - 6 + ConfigManager.getDynamicYOffset();
            }
        }

        x += cfg.xOffset;
        y += cfg.yOffset;

        if (cfg.theme == Theme.RAINBOW) {
            A.drawRainbowString(ctx, tr, text, x, y, true);
        } else {
            int color = cfg.theme.getColor();
            ctx.drawText(tr, text, x, y, color, true);
        }
    }
}
