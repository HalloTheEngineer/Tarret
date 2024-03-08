package de.hallotheengineer.tarret.util;

import de.hallotheengineer.tarret.client.TarretClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyHandler{
    public static KeyBinding adminMenu = new KeyBinding(
            "key.tarret.admin",
            InputUtil.Type.KEYSYM,
            -1, "key.categories.misc"
    );
    public static void tick(MinecraftClient client) {
        if (client.player == null) return;
        while (adminMenu.wasPressed()) {
            if (TarretClient.currentAccessToken != null) client.setScreen(TarretClient.getAdminScreen());
            else client.setScreen(TarretClient.getLoginScreen());
        }
    }

}
