package com.antiklu.aplikasi.settings;

import android.util.Base64;

public class Client {
    public static final String APP_VERSION = "1";

    public static String bangsatkau(String string) {
        String output = "";
        try {
            byte[] data = string.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            output = base64;
        } catch (Exception e) {

        }
        return output;
    }
}
