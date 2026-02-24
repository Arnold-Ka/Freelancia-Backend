package com.hackers.freelancia.config;

import java.util.UUID;

public class Utils {

    /**
     * Génère un identifiant unique pour les profils freelances.
     *
     * @return un identifiant unique sous la forme "free{UUID}cia"
     */
    public static String generateId(){
        return "free"+UUID.randomUUID().toString()+"cia";
    }
}
