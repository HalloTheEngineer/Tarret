package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ToggleFullscreenC2SPacket(String accessToken, String player) implements FabricPacket {
    public static final PacketType<ToggleFullscreenC2SPacket> TYPE = PacketType.create(
            ModPackets.TOGGLE_FULLSCREEN_C2S_PACKET, ToggleFullscreenC2SPacket::read
    );

    private static ToggleFullscreenC2SPacket read(PacketByteBuf buffer) {
        return new ToggleFullscreenC2SPacket(buffer.readString(), buffer.readString());
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
