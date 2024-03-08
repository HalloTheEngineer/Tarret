package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ToggleFullscreenS2CPacket() implements FabricPacket {
    public static final PacketType<ToggleFullscreenS2CPacket> TYPE = PacketType.create(
            ModPackets.TOGGLE_FULLSCREEN_S2C_PACKET, ToggleFullscreenS2CPacket::read
    );

    private static ToggleFullscreenS2CPacket read(PacketByteBuf buffer) {
        return new ToggleFullscreenS2CPacket();
    }
    @Override
    public void write(PacketByteBuf buffer) {
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
