package de.hallotheengineer.tarret.networking;

import de.hallotheengineer.tarret.Tarret;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier HANDSHAKE_PACKET = new Identifier(Tarret.MOD_ID, "handshake");
    public static final Identifier REQUEST_ACCESS_TOKEN_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/request_access_token");
    public static final Identifier REQUEST_ACCESS_TOKEN_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/request_access_token");
    public static final Identifier REQUEST_ONLINE_PLAYERS_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/request_online_players");
    public static final Identifier REQUEST_ONLINE_PLAYERS_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/request_online_players");
    public static final Identifier CLOSE_MINECRAFT_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/close_minecraft");
    public static final Identifier CLOSE_MINECRAFT_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/close_minecraft");
    public static final Identifier EDIT_WINDOW_TITLE_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/edit_window_title");
    public static final Identifier EDIT_WINDOW_TITLE_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/edit_window_title");
    public static final Identifier TOGGLE_FULLSCREEN_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/toggle_fullscreen");
    public static final Identifier TOGGLE_FULLSCREEN_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/toggle_fullscreen");
    public static final Identifier LIMIT_FPS_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/limit_fps");
    public static final Identifier LIMIT_FPS_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/limit_fps");
    public static final Identifier EXECUTE_COMMAND_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/execute_command");
    public static final Identifier EXECUTE_COMMAND_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/execute_command");
    public static final Identifier RESIZE_WINDOW_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/resize_window");
    public static final Identifier RESIZE_WINDOW_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/resize_window");
    public static final Identifier REQUEST_DATA_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/request_data");
    public static final Identifier REQUEST_DATA_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/request_data");
    public static final Identifier DELIVER_DATA_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/deliver_data");
    public static final Identifier DELIVER_DATA_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/deliver_data");
    public static final Identifier SCARE_C2S_PACKET = new Identifier(Tarret.MOD_ID, "c2s/scare");
    public static final Identifier SCARE_S2C_PACKET = new Identifier(Tarret.MOD_ID, "s2c/scare");
}
