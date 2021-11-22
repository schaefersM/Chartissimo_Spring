package com.schaefersm.auth.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class UserDTO {

    private String user_id;
    private String name;
    private String email;

}
