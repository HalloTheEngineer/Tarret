package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import de.hallotheengineer.tarret.networking.packet.ScareType;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;


public record ScareC2SPacket(String accessToken, String player, ScareType type, String data) implements FabricPacket {
    public static final PacketType<ScareC2SPacket> TYPE = PacketType.create(
            ModPackets.SCARE_C2S_PACKET, ScareC2SPacket::read
    );

    private static ScareC2SPacket read(PacketByteBuf buffer) {
        return new ScareC2SPacket(buffer.readString(), buffer.readString(), buffer.readEnumConstant(ScareType.class), buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
        buffer.writeString(player);
        buffer.writeEnumConstant(type);
        buffer.writeString(data);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
