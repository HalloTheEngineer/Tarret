package de.hallotheengineer.tarret.networking.task;

import de.hallotheengineer.tarret.Tarret;
import de.hallotheengineer.tarret.networking.packet.HandshakePacket;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerConfigurationTask;

import java.util.function.Consumer;

public record HandshakeTask() implements ServerPlayerConfigurationTask {
    public static final Key KEY = new Key(Tarret.MOD_ID+":handshake");
    @Override
    public void sendPacket(Consumer<Packet<?>> sender) {
        sender.accept(ServerConfigurationNetworking.createS2CPacket(new HandshakePacket()));
    }

    @Override
    public Key getKey() {
        return KEY;
    }
}
