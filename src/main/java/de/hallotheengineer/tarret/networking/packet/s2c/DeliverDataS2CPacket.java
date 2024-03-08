package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import de.hallotheengineer.tarret.networking.packet.DataType;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record DeliverDataS2CPacket(DataType type, String data, String source) implements FabricPacket {
    public static final PacketType<DeliverDataS2CPacket> TYPE = PacketType.create(
            ModPackets.DELIVER_DATA_S2C_PACKET, DeliverDataS2CPacket::read
    );

    private static DeliverDataS2CPacket read(PacketByteBuf buffer) {
        return new DeliverDataS2CPacket(buffer.readEnumConstant(DataType.class), buffer.readString(), buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeEnumConstant(type);
        buffer.writeString(data);
        buffer.writeString(source);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
