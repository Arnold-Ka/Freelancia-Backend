package com.hackers.freelancia.config;

import java.util.UUID;

public class Utils {

    public static String generateId(){
        return "FREE"+UUID.randomUUID().toString()+"CIA";
    }
}
