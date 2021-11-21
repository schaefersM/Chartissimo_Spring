package com.schaefersm.auth.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private UserDTO user;
    private boolean isAuthenticated = true;

}
