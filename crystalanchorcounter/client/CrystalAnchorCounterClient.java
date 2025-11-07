package me.cutebow.crystalanchorcounter.client;

import com.mojang.brigadier.CommandDispatcher;
import me.cutebow.crystalanchorcounter.client.commands.CounterCommand;
import me.cutebow.crystalanchorcounter.client.config.ConfigManager;
import me.cutebow.crystalanchorcounter.client.util.B;
import me.cutebow.crystalanchorcounter.client.util.Mode;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class CrystalAnchorCounterClient implements ClientModInitializer {
    private static final MinecraftClient D = MinecraftClient.getInstance();
    private static int I = 0;
    private static int A = 0;
    private static int K = 0;
    private static int E = 0;
    private static int J = 0;
    private static final Map<UUID, Integer> H = new HashMap<>();
    private static final Map<Integer, Integer> F = new HashMap<>();
    private static final Map<Integer, Vec3d> L = new HashMap<>();
    private static final int G = 16;
    private static final Map<BlockPos, Integer> BPOS = new HashMap<>();
    private static final int C = 12;

    @Override
    public void onInitializeClient() {
        ConfigManager.load();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                CounterCommand.register((CommandDispatcher<FabricClientCommandSource>) dispatcher)
        );

        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (!(world instanceof ClientWorld)) return ActionResult.PASS;
            if (!(hit instanceof BlockHitResult bhr)) return ActionResult.PASS;
            BlockPos pos = bhr.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (!(state.getBlock() instanceof RespawnAnchorBlock)) return ActionResult.PASS;
            if (player.getStackInHand(hand).isOf(Items.GLOWSTONE)) return ActionResult.PASS;
            if (state.contains(RespawnAnchorBlock.CHARGES) && state.get(RespawnAnchorBlock.CHARGES) > 0) {
                BPOS.put(pos.toImmutable(), C);
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (!(world instanceof ClientWorld)) return ActionResult.PASS;
            if (!(entity instanceof EndCrystalEntity crystal)) return ActionResult.PASS;
            H.put(crystal.getUuid(), G);
            F.put(crystal.getId(), G);
            L.put(crystal.getId(), crystal.getPos());
            return ActionResult.PASS;
        });

        ClientEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (!(entity instanceof EndCrystalEntity)) return;
            boolean attacked = false;
            UUID u = entity.getUuid();
            Integer was = H.remove(u);
            if (was != null) attacked = true;
            Integer idWas = F.remove(entity.getId());
            L.remove(entity.getId());
            if (attacked || idWas != null) ++I;
        });

        ClientTickEvents.END_WORLD_TICK.register(world -> {
            if (world == null || D.player == null) return;

            if (!H.isEmpty() || !F.isEmpty()) {
                H.replaceAll((u, n) -> n - 1);
                F.replaceAll((id, n) -> n - 1);
                F.entrySet().removeIf(e -> e.getValue() <= 0 && !world.isClient());
                H.values().removeIf(n -> n <= 0);

                F.entrySet().removeIf(e -> {
                    int id = e.getKey();
                    int ttl = e.getValue();
                    if (ttl <= 0) return true;
                    Entity ent = world.getEntityById(id);
                    if (ent == null) {
                        ++I;
                        H.values().removeIf(n -> true);
                        L.remove(id);
                        return true;
                    }
                    return false;
                });

                if (!L.isEmpty()) {
                    L.entrySet().removeIf(e -> {
                        int id = e.getKey();
                        Vec3d pos = e.getValue();
                        int ttl = F.getOrDefault(id, 0);
                        if (ttl <= 0) return true;
                        Box box = new Box(pos, pos).expand(0.6, 1.2, 0.6);
                        boolean stillThere = !world.getEntitiesByClass(EndCrystalEntity.class, box, ec -> true).isEmpty();
                        if (!stillThere) {
                            ++I;
                            F.remove(id);
                            H.values().removeIf(n -> true);
                            return true;
                        }
                        return false;
                    });
                }
            }

            if (!BPOS.isEmpty()) {
                BPOS.replaceAll((p, n) -> n - 1);
                BPOS.entrySet().removeIf(e -> {
                    BlockPos p = e.getKey();
                    int n = e.getValue();
                    boolean air = world.isAir(p);
                    if (air && n > 0) {
                        ++A;
                        return true;
                    }
                    return n <= 0;
                });
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (++J >= 20) {
                K = I;
                E = A;
                I = 0;
                A = 0;
                J = 0;
            }
        });

        HudRenderCallback.EVENT.register((DrawContext ctx, RenderTickCounter tickCounter) -> {
            if (D.player == null) return;
            Mode m = ConfigManager.config.mode;
            if (m == Mode.LIVE) {
                B.drawHud(ctx, String.valueOf(I), String.valueOf(A));
            } else if (m == Mode.AVG) {
                B.drawHud(ctx, String.valueOf(K), String.valueOf(E));
            } else if (m == Mode.BOTH) {
                B.drawHud(ctx, String.format("%d (avg %d)", I, K), String.format("%d (avg %d)", A, E));
            } else {
                B.drawHud(ctx, String.valueOf(I), String.valueOf(A));
            }
        });
    }
}
