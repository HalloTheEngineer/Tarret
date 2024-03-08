package de.hallotheengineer.tarret.networking.packet;

public enum SoundType {
    SCARY_ALERT("Scary Alert", "scary_alert.au"),
    JUMPSCARE_1("JS 1", "js1.au"),
    JUMPSCARE_2("JS 2", "js2.au")
    ;
    public final String displayName;
    public final String fileName;

    SoundType(String displayName, String fileName) {
        this.displayName = displayName;
        this.fileName = fileName;
    }
    public static SoundType of(String fileName) {
        for (SoundType s : SoundType.values()) {
            if (s.fileName.equals(fileName)) return s;
        }
        return null;
    }
}
