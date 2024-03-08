package de.hallotheengineer.tarret.networking;


import de.hallotheengineer.tarret.Tarret;
import de.hallotheengineer.tarret.networking.packet.HandshakePacket;
import de.hallotheengineer.tarret.networking.packet.c2s.*;
import de.hallotheengineer.tarret.networking.packet.s2c.*;
import de.hallotheengineer.tarret.networking.task.HandshakeTask;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConfigurationNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.security.SecureRandom;
import java.util.*;

public class ModPacketsC2S {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final Map<UUID, FabricPacket> ongoingRequests = new HashMap<>();
    public static void register() {
        ServerConfigurationConnectionEvents.CONFIGURE.register(ModPacketsC2S::handshake);
        ServerConfigurationNetworking.registerGlobalReceiver(HandshakePacket.TYPE, ModPacketsC2S::handleHandshakeReply);

        ServerPlayNetworking.registerGlobalReceiver(RequestAccessTokenC2SPacket.TYPE, ModPacketsC2S::handleSessionRequest);
        ServerPlayNetworking.registerGlobalReceiver(RequestOnlinePlayersC2SPacket.TYPE, ModPacketsC2S::handlePlayersRequest);
        ServerPlayNetworking.registerGlobalReceiver(CloseMinecraftC2SPacket.TYPE, ModPacketsC2S::handleCloseMinecraftRequest);
        ServerPlayNetworking.registerGlobalReceiver(EditWindowTitleC2SPacket.TYPE, ModPacketsC2S::handleEditTileRequest);
        ServerPlayNetworking.registerGlobalReceiver(ToggleFullscreenC2SPacket.TYPE, ModPacketsC2S::handleToggleFullscreenRequest);
        ServerPlayNetworking.registerGlobalReceiver(LimitFPSC2SPacket.TYPE, ModPacketsC2S::handleLimitFPSRequest);
        ServerPlayNetworking.registerGlobalReceiver(ExecuteCommandC2SPacket.TYPE, ModPacketsC2S::handleExecuteCommandRequest);
        ServerPlayNetworking.registerGlobalReceiver(RequestDataC2SPacket.TYPE, ModPacketsC2S::handleDataRequest);
        ServerPlayNetworking.registerGlobalReceiver(DeliverDataC2SPacket.TYPE, ModPacketsC2S::handleDataDelivery);
        ServerPlayNetworking.registerGlobalReceiver(ResizeWindowC2SPacket.TYPE, ModPacketsC2S::handleWindowResize);
        ServerPlayNetworking.registerGlobalReceiver(ScareC2SPacket.TYPE, ModPacketsC2S::handleScare);
    }

    private static void handleScare(ScareC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            if (target != null) {
                server.execute(() -> ServerPlayNetworking.send(target, new ScareS2CPacket(packet.type(), packet.data())));
            } else player.sendMessage(Text.translatable("chat.tarret.missing_target").formatted(Formatting.RED));
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleWindowResize(ResizeWindowC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            if (target != null) {
                server.execute(() -> ServerPlayNetworking.send(target, new ResizeWindowS2CPacket(packet.width(), packet.height())));
            } else player.sendMessage(Text.translatable("chat.tarret.missing_target").formatted(Formatting.RED));
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleDataDelivery(DeliverDataC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (ongoingRequests.containsKey(packet.target())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.target());
            if (target != null) server.execute(() -> ServerPlayNetworking.send(target, new DeliverDataS2CPacket(packet.type(), packet.data(), packet.source())));
            ongoingRequests.remove(packet.target());
        }
    }

    private static void handleDataRequest(RequestDataC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            if (target != null) {
                if (ongoingRequests.putIfAbsent(player.getUuid(), packet) != null) player.sendMessage(Text.translatable("chat.tarret.ongoing_request").formatted(Formatting.RED));
                server.execute(() -> ServerPlayNetworking.send(target, new RequestDataS2CPacket(packet.type(), player.getUuid())));
            } else player.sendMessage(Text.translatable("chat.tarret.missing_target").formatted(Formatting.RED));
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleExecuteCommandRequest(ExecuteCommandC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            if (target != null) server.execute(() -> ServerPlayNetworking.send(target, new ExecuteCommandS2CPacket(packet.command())));
            else player.sendMessage(Text.translatable("chat.tarret.missing_target"));
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleLimitFPSRequest(LimitFPSC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            if (target != null) server.execute(() -> ServerPlayNetworking.send(target, new LimitFPSS2CPacket(packet.fps())));
            else player.sendMessage(Text.translatable("chat.tarret.missing_target"));
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleToggleFullscreenRequest(ToggleFullscreenC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            if (target != null) server.execute(() -> ServerPlayNetworking.send(target, new ToggleFullscreenS2CPacket()));
            else player.sendMessage(Text.translatable("chat.tarret.missing_target"));
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handlePlayersRequest(RequestOnlinePlayersC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            List<String> players = server.getPlayerManager().getPlayerList().stream().map(player1 -> player1.getName().getString()).toList();
            server.execute(() -> packetSender.sendPacket(new RequestOnlinePlayersS2CPacket(players)));
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleEditTileRequest(EditWindowTitleC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            String title = packet.title();
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            server.execute(() -> {
                if (target != null) ServerPlayNetworking.send(target, new EditWindowTitleS2CPacket(title));
                else player.sendMessage(Text.translatable("chat.tarret.missing_target"));
            });
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleCloseMinecraftRequest(CloseMinecraftC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        if (validateToken(player.getUuid(), packet.accessToken())) {
            MinecraftServer server = player.getServer();
            ServerPlayerEntity target = server.getPlayerManager().getPlayer(packet.player());
            server.execute(() -> {
                if (target != null) ServerPlayNetworking.send(target, new CloseMinecraftS2CPacket());
                else player.sendMessage(Text.translatable("chat.tarret.missing_target"));
            });
        } else player.sendMessage(Text.translatable("chat.tarret.invalid_token").formatted(Formatting.RED));
    }

    private static void handleSessionRequest(RequestAccessTokenC2SPacket packet, ServerPlayerEntity player, PacketSender packetSender) {
        MinecraftServer server = player.getServer();
        assert server != null;
        if (packet.password().equals(Tarret.CONFIG.password)) {

            String token = generateNewToken();
            Tarret.tokenRegister.putIfAbsent(player.getUuid(), token);

            server.execute(() -> packetSender.sendPacket(new AccessTokenDeliveryS2CPacket(Tarret.tokenRegister.get(player.getUuid()))));
        }
    }

    private static void handshake(ServerConfigurationNetworkHandler handler, MinecraftServer server) {
        if (ServerConfigurationNetworking.canSend(handler, HandshakePacket.TYPE)) {
            handler.addTask(new HandshakeTask());
            Tarret.LOGGER.info("Initializing handshake...");
            return;
        }
        handler.disconnect(Text.literal("This server requires you to install the Tarret mod to play.").formatted(Formatting.RED));
    }
    private static void handleHandshakeReply(HandshakePacket packet, ServerConfigurationNetworkHandler handler, PacketSender responseSender) {
        handler.completeTask(HandshakeTask.KEY);
        Tarret.LOGGER.info("Handshake successfully!");
    }
    public static boolean validateToken(UUID uuid, String accessToken) {
        return Tarret.tokenRegister.containsKey(uuid) && Tarret.tokenRegister.get(uuid).equals(accessToken);
    }
    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
