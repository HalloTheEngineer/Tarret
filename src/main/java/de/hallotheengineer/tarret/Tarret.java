package de.hallotheengineer.tarret;

import de.hallotheengineer.tarret.networking.ModCommands;
import de.hallotheengineer.tarret.networking.ModPacketsC2S;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tarret implements ModInitializer {

    public static final String MOD_ID = "tarret";
    public static final ServerConfig CONFIG = new ServerConfig();
    public static Map<UUID, String> tokenRegister = new HashMap<>();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        ModPacketsC2S.register();
        ModCommands.register();

        CONFIG.load();
    }
}
