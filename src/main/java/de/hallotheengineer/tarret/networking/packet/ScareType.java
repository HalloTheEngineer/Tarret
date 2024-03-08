package de.hallotheengineer.tarret.networking.packet;

public enum ScareType {
    SCARY_ALERT("Scary Alert"),
    SOUND_ALERT("Sound Alert");
    public final String displayName;

    ScareType(String s) {
        this.displayName = s;
    }
}
