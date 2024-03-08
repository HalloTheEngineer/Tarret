package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record RequestAccessTokenC2SPacket(String password) implements FabricPacket {
    public static final PacketType<RequestAccessTokenC2SPacket> TYPE = PacketType.create(
            ModPackets.REQUEST_ACCESS_TOKEN_C2S_PACKET, RequestAccessTokenC2SPacket::read
    );

    private static RequestAccessTokenC2SPacket read(PacketByteBuf buffer) {
        return new RequestAccessTokenC2SPacket(buffer.readString());
    }

    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(password);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
