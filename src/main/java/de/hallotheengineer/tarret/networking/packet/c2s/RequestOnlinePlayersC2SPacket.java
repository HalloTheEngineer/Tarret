package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record RequestOnlinePlayersC2SPacket(String accessToken) implements FabricPacket {
    public static final PacketType<RequestOnlinePlayersC2SPacket> TYPE = PacketType.create(
            ModPackets.REQUEST_ONLINE_PLAYERS_C2S_PACKET, RequestOnlinePlayersC2SPacket::read
    );

    private static RequestOnlinePlayersC2SPacket read(PacketByteBuf buffer) {
        return new RequestOnlinePlayersC2SPacket(buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
