package com.compass.application.adapters;

public interface CryptoAdapter {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
