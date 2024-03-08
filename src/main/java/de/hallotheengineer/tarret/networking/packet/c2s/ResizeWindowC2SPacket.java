package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ResizeWindowC2SPacket(String accessToken, String player, int width, int height) implements FabricPacket {
    public static final PacketType<ResizeWindowC2SPacket> TYPE = PacketType.create(
            ModPackets.RESIZE_WINDOW_C2S_PACKET, ResizeWindowC2SPacket::read
    );

    private static ResizeWindowC2SPacket read(PacketByteBuf buffer) {
        return new ResizeWindowC2SPacket(buffer.readString(), buffer.readString(), buffer.readInt(), buffer.readInt());
    }

    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
        buffer.writeString(player);
        buffer.writeInt(width);
        buffer.writeInt(height);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
