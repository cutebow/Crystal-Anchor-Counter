package me.cutebow.crystalanchorcounter.client.config;

import me.cutebow.crystalanchorcounter.client.util.Mode;
import me.cutebow.crystalanchorcounter.client.util.Position;
import me.cutebow.crystalanchorcounter.client.util.Theme;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CounterConfig {
    public Theme theme = Theme.WHITE;
    public Position position = Position.TOP_LEFT;
    public Mode mode = Mode.LIVE;
    public String format = "short";
    public int xOffset = 0;
    public int yOffset = 0;
    public Boolean hudEnabled = true;
}
