package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record LimitFPSS2CPacket(int fps) implements FabricPacket {
    public static final PacketType<LimitFPSS2CPacket> TYPE = PacketType.create(
            ModPackets.LIMIT_FPS_S2C_PACKET, LimitFPSS2CPacket::read
    );

    private static LimitFPSS2CPacket read(PacketByteBuf buffer) {
        return new LimitFPSS2CPacket(buffer.readInt());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeInt(fps);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
