package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record LimitFPSC2SPacket(String accessToken, String player, int fps) implements FabricPacket {
    public static final PacketType<LimitFPSC2SPacket> TYPE = PacketType.create(
            ModPackets.LIMIT_FPS_C2S_PACKET, LimitFPSC2SPacket::read
    );

    private static LimitFPSC2SPacket read(PacketByteBuf buffer) {
        return new LimitFPSC2SPacket(buffer.readString(), buffer.readString(), buffer.readInt());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
        buffer.writeString(player);
        buffer.writeInt(fps);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
