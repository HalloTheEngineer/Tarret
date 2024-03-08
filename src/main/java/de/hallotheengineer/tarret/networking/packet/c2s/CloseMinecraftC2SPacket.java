package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record CloseMinecraftC2SPacket(String accessToken, String player) implements FabricPacket {
    public static final PacketType<CloseMinecraftC2SPacket> TYPE = PacketType.create(
            ModPackets.CLOSE_MINECRAFT_C2S_PACKET, CloseMinecraftC2SPacket::read
    );

    private static CloseMinecraftC2SPacket read(PacketByteBuf buffer) {
        return new CloseMinecraftC2SPacket(buffer.readString(), buffer.readString());
    }

    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
        buffer.writeString(player);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
