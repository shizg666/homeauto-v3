package com.landleaf.homeauto.mqtt.container;

public interface AuthencationSession {
    boolean auth(String username,String password);
}
