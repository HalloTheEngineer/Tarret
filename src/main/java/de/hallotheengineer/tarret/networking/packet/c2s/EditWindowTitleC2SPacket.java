package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record EditWindowTitleC2SPacket(String accessToken, String player, String title) implements FabricPacket {
    public static final PacketType<EditWindowTitleC2SPacket> TYPE = PacketType.create(
            ModPackets.EDIT_WINDOW_TITLE_C2S_PACKET, EditWindowTitleC2SPacket::read
    );

    private static EditWindowTitleC2SPacket read(PacketByteBuf buffer) {
        return new EditWindowTitleC2SPacket(buffer.readString(), buffer.readString(), buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
        buffer.writeString(player);
        buffer.writeString(title);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
