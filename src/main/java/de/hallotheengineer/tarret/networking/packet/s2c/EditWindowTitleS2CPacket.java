package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record EditWindowTitleS2CPacket(String title) implements FabricPacket {
    public static final PacketType<EditWindowTitleS2CPacket> TYPE = PacketType.create(
            ModPackets.EDIT_WINDOW_TITLE_S2C_PACKET, EditWindowTitleS2CPacket::read
    );

    private static EditWindowTitleS2CPacket read(PacketByteBuf buffer) {
        return new EditWindowTitleS2CPacket(buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(title);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
