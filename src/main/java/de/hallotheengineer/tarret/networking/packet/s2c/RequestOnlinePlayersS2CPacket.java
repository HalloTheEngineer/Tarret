package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

import java.util.List;

public record RequestOnlinePlayersS2CPacket(List<String> players) implements FabricPacket {
    public static final PacketType<RequestOnlinePlayersS2CPacket> TYPE = PacketType.create(
            ModPackets.REQUEST_ONLINE_PLAYERS_S2C_PACKET, RequestOnlinePlayersS2CPacket::read
    );

    private static RequestOnlinePlayersS2CPacket read(PacketByteBuf buffer) {
        return new RequestOnlinePlayersS2CPacket(buffer.readList(PacketByteBuf::readString));
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeCollection(players, PacketByteBuf::writeString);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
