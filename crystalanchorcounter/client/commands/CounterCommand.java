package me.cutebow.crystalanchorcounter.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.cutebow.crystalanchorcounter.client.config.ConfigManager;
import me.cutebow.crystalanchorcounter.client.util.Mode;
import me.cutebow.crystalanchorcounter.client.util.Position;
import me.cutebow.crystalanchorcounter.client.util.Theme;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CounterCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(build("counter"));
        dispatcher.register(build("cac"));
    }

    private static LiteralArgumentBuilder<FabricClientCommandSource> build(String root) {
        LiteralArgumentBuilder<FabricClientCommandSource> b = ClientCommandManager.literal(root);

        b.then(ClientCommandManager.literal("mode")
                .then(ClientCommandManager.literal("live").executes(ctx -> {
                    ConfigManager.setMode(Mode.LIVE);
                    ConfigManager.save();
                    ctx.getSource().sendFeedback(Text.literal("Mode set to live"));
                    return 1;
                }))
                .then(ClientCommandManager.literal("avg").executes(ctx -> {
                    ConfigManager.setMode(Mode.AVG);
                    ConfigManager.save();
                    ctx.getSource().sendFeedback(Text.literal("Mode set to avg"));
                    return 1;
                }))
                .then(ClientCommandManager.literal("both").executes(ctx -> {
                    ConfigManager.setMode(Mode.BOTH);
                    ConfigManager.save();
                    ctx.getSource().sendFeedback(Text.literal("Mode set to both"));
                    return 1;
                }))
        );

        b.then(ClientCommandManager.literal("theme")
                .then(ClientCommandManager.literal("white").executes(ctx -> { ConfigManager.setTheme(Theme.WHITE); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to white")); return 1; }))
                .then(ClientCommandManager.literal("red").executes(ctx -> { ConfigManager.setTheme(Theme.RED); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to red")); return 1; }))
                .then(ClientCommandManager.literal("green").executes(ctx -> { ConfigManager.setTheme(Theme.GREEN); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to green")); return 1; }))
                .then(ClientCommandManager.literal("blue").executes(ctx -> { ConfigManager.setTheme(Theme.BLUE); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to blue")); return 1; }))
                .then(ClientCommandManager.literal("yellow").executes(ctx -> { ConfigManager.setTheme(Theme.YELLOW); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to yellow")); return 1; }))
                .then(ClientCommandManager.literal("aqua").executes(ctx -> { ConfigManager.setTheme(Theme.AQUA); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to aqua")); return 1; }))
                .then(ClientCommandManager.literal("purple").executes(ctx -> { ConfigManager.setTheme(Theme.PURPLE); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to purple")); return 1; }))
                .then(ClientCommandManager.literal("pink").executes(ctx -> { ConfigManager.setTheme(Theme.PINK); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to pink")); return 1; }))
                .then(ClientCommandManager.literal("cyan").executes(ctx -> { ConfigManager.setTheme(Theme.CYAN); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to cyan")); return 1; }))
                .then(ClientCommandManager.literal("gray").executes(ctx -> { ConfigManager.setTheme(Theme.GRAY); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to gray")); return 1; }))
                .then(ClientCommandManager.literal("lime").executes(ctx -> { ConfigManager.setTheme(Theme.LIME); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to lime")); return 1; }))
                .then(ClientCommandManager.literal("magenta").executes(ctx -> { ConfigManager.setTheme(Theme.MAGENTA); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to magenta")); return 1; }))
                .then(ClientCommandManager.literal("gold").executes(ctx -> { ConfigManager.setTheme(Theme.GOLD); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to gold")); return 1; }))
                .then(ClientCommandManager.literal("orange").executes(ctx -> { ConfigManager.setTheme(Theme.ORANGE); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to orange")); return 1; }))
                .then(ClientCommandManager.literal("rainbow").executes(ctx -> { ConfigManager.setTheme(Theme.RAINBOW); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Theme set to rainbow")); return 1; }))
        );

        b.then(ClientCommandManager.literal("position")
                .then(ClientCommandManager.literal("top-left").executes(ctx -> { ConfigManager.setPosition(Position.TOP_LEFT); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Position set to top-left")); return 1; }))
                .then(ClientCommandManager.literal("top-right").executes(ctx -> { ConfigManager.setPosition(Position.TOP_RIGHT); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Position set to top-right")); return 1; }))
                .then(ClientCommandManager.literal("bottom-left").executes(ctx -> { ConfigManager.setPosition(Position.BOTTOM_LEFT); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Position set to bottom-left")); return 1; }))
                .then(ClientCommandManager.literal("bottom-right").executes(ctx -> { ConfigManager.setPosition(Position.BOTTOM_RIGHT); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Position set to bottom-right")); return 1; }))
                .then(ClientCommandManager.literal("hotbar").executes(ctx -> { ConfigManager.setPosition(Position.HOTBAR); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Position set to hotbar")); return 1; }))
        );

        b.then(ClientCommandManager.literal("format")
                .then(ClientCommandManager.literal("short").executes(ctx -> { ConfigManager.setFormat("short"); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Format set to short")); return 1; }))
                .then(ClientCommandManager.literal("long").executes(ctx -> { ConfigManager.setFormat("long"); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Format set to long")); return 1; }))
                .then(ClientCommandManager.literal("pretty").executes(ctx -> { ConfigManager.setFormat("pretty"); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Format set to pretty")); return 1; }))
                .then(ClientCommandManager.literal("chat").executes(ctx -> { ConfigManager.setFormat("chat"); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("Format set to chat")); return 1; }))
        );

        b.then(ClientCommandManager.literal("hud")
                .then(ClientCommandManager.literal("on").executes(ctx -> { ConfigManager.setHudEnabled(true); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("HUD enabled")); return 1; }))
                .then(ClientCommandManager.literal("off").executes(ctx -> { ConfigManager.setHudEnabled(false); ConfigManager.save(); ctx.getSource().sendFeedback(Text.literal("HUD disabled")); return 1; }))
        );

        return b;
    }
}
