package me.cutebow.crystalanchorcounter.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.awt.Color;

@Environment(EnvType.CLIENT)
public class C {
    public static int rainbow() {
        long t = System.currentTimeMillis();
        float hue = (t % 3000L) / 3000f;
        return Color.HSBtoRGB(hue, 1f, 1f);
    }
}
