package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import de.hallotheengineer.tarret.networking.packet.DataType;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record RequestDataC2SPacket(String accessToken, String player, DataType type) implements FabricPacket {
    public static final PacketType<RequestDataC2SPacket> TYPE = PacketType.create(
            ModPackets.REQUEST_DATA_C2S_PACKET, RequestDataC2SPacket::read
    );

    private static RequestDataC2SPacket read(PacketByteBuf buffer) {
        return new RequestDataC2SPacket(buffer.readString(), buffer.readString(), buffer.readEnumConstant(DataType.class));
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
        buffer.writeString(player);
        buffer.writeEnumConstant(type);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
