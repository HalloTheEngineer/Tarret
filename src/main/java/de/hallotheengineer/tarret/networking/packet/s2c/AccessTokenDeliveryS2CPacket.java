package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record AccessTokenDeliveryS2CPacket(String accessToken) implements FabricPacket {
    public static final PacketType<AccessTokenDeliveryS2CPacket> TYPE = PacketType.create(
            ModPackets.REQUEST_ACCESS_TOKEN_S2C_PACKET, AccessTokenDeliveryS2CPacket::read
    );
    private static AccessTokenDeliveryS2CPacket read(PacketByteBuf buffer) {
        return new AccessTokenDeliveryS2CPacket(buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
