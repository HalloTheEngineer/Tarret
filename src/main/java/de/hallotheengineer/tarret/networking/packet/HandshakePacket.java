package de.hallotheengineer.tarret.networking.packet;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record HandshakePacket() implements FabricPacket {
    public static final PacketType<HandshakePacket> TYPE = PacketType.create(
            ModPackets.HANDSHAKE_PACKET,HandshakePacket::read
    );
    private static HandshakePacket read(PacketByteBuf buffer) {
        return new HandshakePacket();
    }
    @Override
    public void write(PacketByteBuf buf) {
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
