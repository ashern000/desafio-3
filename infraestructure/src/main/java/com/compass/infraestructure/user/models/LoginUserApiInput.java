package com.compass.infraestructure.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginUserApiInput(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {
}
