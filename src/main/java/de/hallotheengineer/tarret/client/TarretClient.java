package de.hallotheengineer.tarret.client;

import de.hallotheengineer.tarret.client.gui.AdminScreen;
import de.hallotheengineer.tarret.client.gui.LoginScreen;
import de.hallotheengineer.tarret.networking.ModPacketsS2C;
import de.hallotheengineer.tarret.util.KeyHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;

public class TarretClient implements ClientModInitializer {
    public static MinecraftClient INSTANCE = MinecraftClient.getInstance();
    public static String forcedTitle = "";
    private static LoginScreen LOGIN_SCREEN;
    private static AdminScreen ADMIN_SCREEN;
    public static String currentAccessToken;
    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KeyHandler.adminMenu);
        ModPacketsS2C.register();
        LOGIN_SCREEN = new LoginScreen();

        ClientTickEvents.END_CLIENT_TICK.register(KeyHandler::tick);
    }

    public static LoginScreen getLoginScreen() {
        return LOGIN_SCREEN;
    }
    public static AdminScreen getAdminScreen() {
        return ADMIN_SCREEN;
    }
    public static void setAdminScreen(AdminScreen adminScreen) {
        ADMIN_SCREEN = adminScreen;
    }
}
