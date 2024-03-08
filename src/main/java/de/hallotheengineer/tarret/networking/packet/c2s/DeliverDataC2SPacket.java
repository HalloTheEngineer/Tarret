package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import de.hallotheengineer.tarret.networking.packet.DataType;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public record DeliverDataC2SPacket(DataType type, String data, UUID target, String source) implements FabricPacket {
    public static final PacketType<DeliverDataC2SPacket> TYPE = PacketType.create(
            ModPackets.DELIVER_DATA_C2S_PACKET, DeliverDataC2SPacket::read
    );

    private static DeliverDataC2SPacket read(PacketByteBuf buffer) {
        return new DeliverDataC2SPacket(buffer.readEnumConstant(DataType.class), buffer.readString(), buffer.readUuid(), buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeEnumConstant(type);
        buffer.writeString(data);
        buffer.writeUuid(target);
        buffer.writeString(source);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
