package me.cutebow.crystalanchorcounter.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum Theme {
    WHITE(0xFFFFFF),
    RED(0xFF5555),
    GREEN(0x55FF55),
    BLUE(0x5555FF),
    YELLOW(0xFFFF55),
    AQUA(0x55FFFF),
    PURPLE(0xAA55FF),
    PINK(0xFF77AA),
    CYAN(0x00FFFF),
    GRAY(0xAAAAAA),
    LIME(0x66FF66),
    MAGENTA(0xFF55FF),
    GOLD(0xFFAA00),
    ORANGE(0xFFAA55),
    RAINBOW(-1);

    private final int color;

    Theme(int color) {
        this.color = color;
    }

    public int getColor() {
        return this == RAINBOW ? C.rainbow() : color;
    }

    public static Theme fromString(String s) {
        if (s == null) return WHITE;
        try {
            return Theme.valueOf(s.trim().toUpperCase().replace(" ", "_"));
        } catch (Exception e) {
            return WHITE;
        }
    }
}
