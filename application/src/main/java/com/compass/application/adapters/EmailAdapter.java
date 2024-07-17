package com.compass.application.adapters;

import com.compass.domain.user.Email;

public interface EmailAdapter {
    String send(Email email, String message, String token);
}
