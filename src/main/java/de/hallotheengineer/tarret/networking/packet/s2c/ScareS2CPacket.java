package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import de.hallotheengineer.tarret.networking.packet.ScareType;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ScareS2CPacket(ScareType type, String data) implements FabricPacket {
    public static final PacketType<ScareS2CPacket> TYPE = PacketType.create(
            ModPackets.SCARE_S2C_PACKET, ScareS2CPacket::read
    );

    private static ScareS2CPacket read(PacketByteBuf buffer) {
        return new ScareS2CPacket(buffer.readEnumConstant(ScareType.class), buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeEnumConstant(type);
        buffer.writeString(data);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
