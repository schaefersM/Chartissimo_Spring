package com.schaefersm.auth.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
@Document(collection = "jwtTokens")
public class JwtToken {

    @Id
    private String _id;
    private Date expirationDate;
    private String token;

    public JwtToken(Date expirationDate, String token) {
        this.expirationDate = expirationDate;
        this.token = token;
    }

}
