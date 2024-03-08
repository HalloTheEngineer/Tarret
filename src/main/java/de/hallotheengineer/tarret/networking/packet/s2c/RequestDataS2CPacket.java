package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import de.hallotheengineer.tarret.networking.packet.DataType;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public record RequestDataS2CPacket(DataType type, UUID target) implements FabricPacket {
    public static final PacketType<RequestDataS2CPacket> TYPE = PacketType.create(
            ModPackets.REQUEST_DATA_S2C_PACKET, RequestDataS2CPacket::read
    );

    private static RequestDataS2CPacket read(PacketByteBuf buffer) {
        return new RequestDataS2CPacket(buffer.readEnumConstant(DataType.class), buffer.readUuid());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeEnumConstant(type);
        buffer.writeUuid(target);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
