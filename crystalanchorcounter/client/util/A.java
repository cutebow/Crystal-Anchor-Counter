package me.cutebow.crystalanchorcounter.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;

@Environment(EnvType.CLIENT)
public class A {
    public static void drawRainbowString(DrawContext ctx, TextRenderer tr, String s, int x, int y, boolean shadow) {
        long t = System.currentTimeMillis();
        float period = 3000f;
        float perCharShift = 0.03f;
        float sat = 1f;
        float bri = 1f;
        int cx = x;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            float base = (t % (long) period) / period;
            float hue = (base + i * perCharShift) % 1f;
            int rgb = Color.HSBtoRGB(hue, sat, bri) & 0xFFFFFF;
            ctx.drawText(tr, String.valueOf(ch), cx, y, rgb, shadow);
            cx += tr.getWidth(String.valueOf(ch));
        }
    }
}
