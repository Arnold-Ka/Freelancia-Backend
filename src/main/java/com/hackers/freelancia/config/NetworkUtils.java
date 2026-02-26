package com.hackers.freelancia.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkUtils {

    public static String getPublicIP() {
        try {
            URL url = new URL("https://api.ipify.org"); // service gratuit pour IP publique
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "127.0.0.1"; 
        }
    }
}