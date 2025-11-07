package me.cutebow.crystalanchorcounter.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.cutebow.crystalanchorcounter.client.util.Mode;
import me.cutebow.crystalanchorcounter.client.util.Position;
import me.cutebow.crystalanchorcounter.client.util.Theme;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ConfigManager {
    public static CounterConfig config = new CounterConfig();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/crystal_anchor_counter.json");

    public static void setTheme(Theme theme) {
        config.theme = theme;
        save();
    }

    public static void setPosition(Position position) {
        config.position = position;
        save();
    }

    public static void setFormat(String format) {
        config.format = format;
        save();
    }

    public static void setMode(Mode mode) {
        config.mode = mode;
        save();
    }

    public static void setHudEnabled(boolean enabled) {
        config.hudEnabled = enabled;
        save();
    }

    public static int getDynamicYOffset() {
        if (config.position == Position.HOTBAR) {
            MinecraftClient mc = MinecraftClient.getInstance();
            Objects.requireNonNull(mc.options);
            int n = 9;
            boolean hasInv = mc.player != null && !mc.player.getInventory().isEmpty();
            return -(hasInv ? n * 3 : n * 2);
        }
        return config.yOffset;
    }

    public static void load() {
        try {
            if (CONFIG_FILE.exists()) {
                try (Reader r = new FileReader(CONFIG_FILE)) {
                    config = GSON.fromJson(r, CounterConfig.class);
                }
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean changed = false;
        if (config.theme == null) {
            config.theme = Theme.WHITE;
            changed = true;
        }
        if (config.position == null) {
            config.position = Position.HOTBAR;
            changed = true;
        }
        if (config.mode == null) {
            config.mode = Mode.LIVE;
            changed = true;
        }
        if (config.format == null) {
            config.format = "short";
            changed = true;
        }
        if (config.hudEnabled == null) {
            config.hudEnabled = true;
            changed = true;
        }
        if (changed) {
            save();
        }
    }

    public static void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter w = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(config, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
