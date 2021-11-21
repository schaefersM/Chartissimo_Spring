package com.schaefersm.auth.dto;

import com.schaefersm.auth.model.AuthResponse;
import com.schaefersm.auth.model.User;
import lombok.Data;

@Data
public class AuthResponseDTO {

    private UserDTO user;
    private boolean isAuthenticated = true;

}
