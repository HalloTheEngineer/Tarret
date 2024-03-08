package de.hallotheengineer.tarret;


import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static de.hallotheengineer.tarret.Tarret.LOGGER;

public class ServerConfig {

    public static final String PASSWORD_KEY = "adminPassword";
    public String password = "PASSWORD HERE";


    public void writeTo(Properties properties) {
        properties.setProperty(PASSWORD_KEY, password);
    }

    public void readFrom(Properties properties) {
        this.password = properties.getProperty(PASSWORD_KEY);
    }

    public void save() {
        Properties properties = new Properties();
        writeTo(properties);
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(Tarret.MOD_ID+".properties");
        if(!Files.exists(configPath)) {
            try {
                Files.createDirectories(configPath);
                Files.createFile(configPath);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            properties.store(Files.newOutputStream(configPath), "Configuration file for Tarret");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Properties properties = new Properties();
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(Tarret.MOD_ID+".properties");
        if(!Files.exists(configPath)) {
            try {
                Files.createFile(configPath);
                save();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            properties.load(Files.newInputStream(configPath));
            if (properties.get(PASSWORD_KEY) == "PASSWORD HERE") LOGGER.warn("Update the server password!");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        readFrom(properties);
    }
}
