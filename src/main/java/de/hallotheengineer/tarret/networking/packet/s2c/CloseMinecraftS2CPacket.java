package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record CloseMinecraftS2CPacket() implements FabricPacket {
    public static final PacketType<CloseMinecraftS2CPacket> TYPE = PacketType.create(
            ModPackets.CLOSE_MINECRAFT_S2C_PACKET, CloseMinecraftS2CPacket::read
    );

    private static CloseMinecraftS2CPacket read(PacketByteBuf buf) {
        return new CloseMinecraftS2CPacket();
    }

    @Override
    public void write(PacketByteBuf buf) {
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
