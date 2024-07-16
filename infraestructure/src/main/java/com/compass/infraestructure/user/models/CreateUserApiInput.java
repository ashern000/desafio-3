package com.compass.infraestructure.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserApiInput(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("active") Boolean active,
        @JsonProperty("role") String role
) {
}
