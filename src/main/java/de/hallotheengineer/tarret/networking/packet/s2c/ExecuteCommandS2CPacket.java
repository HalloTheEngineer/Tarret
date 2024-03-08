package de.hallotheengineer.tarret.networking.packet.s2c;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ExecuteCommandS2CPacket(String command) implements FabricPacket {
    public static final PacketType<ExecuteCommandS2CPacket> TYPE = PacketType.create(
            ModPackets.EXECUTE_COMMAND_S2C_PACKET, ExecuteCommandS2CPacket::read
    );

    private static ExecuteCommandS2CPacket read(PacketByteBuf buffer) {
        return new ExecuteCommandS2CPacket(buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(command);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
