package com.schaefersm.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {

    private User user;
    private boolean isAuthenticated = true;

    public AuthResponse(User authenticatedUser) {
        this.user = authenticatedUser;
    }

}
