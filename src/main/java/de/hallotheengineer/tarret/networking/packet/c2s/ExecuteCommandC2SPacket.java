package de.hallotheengineer.tarret.networking.packet.c2s;

import de.hallotheengineer.tarret.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ExecuteCommandC2SPacket(String accessToken, String player, String command) implements FabricPacket {
    public static final PacketType<ExecuteCommandC2SPacket> TYPE = PacketType.create(
            ModPackets.EXECUTE_COMMAND_C2S_PACKET, ExecuteCommandC2SPacket::read
    );

    private static ExecuteCommandC2SPacket read(PacketByteBuf buffer) {
        return new ExecuteCommandC2SPacket(buffer.readString(), buffer.readString(), buffer.readString());
    }
    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeString(accessToken);
        buffer.writeString(player);
        buffer.writeString(command);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
