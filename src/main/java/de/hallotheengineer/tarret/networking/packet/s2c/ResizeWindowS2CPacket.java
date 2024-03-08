package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ResizeWindowS2CPacket(int width, int height) implements FabricPacket {
    public static final PacketType<ResizeWindowS2CPacket> TYPE = PacketType.create(
            ModPackets.RESIZE_WINDOW_S2C_PACKET, ResizeWindowS2CPacket::read
    );

    private static ResizeWindowS2CPacket read(PacketByteBuf buffer) {
        return new ResizeWindowS2CPacket(buffer.readInt(), buffer.readInt());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeInt(width);
        buffer.writeInt(height);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
