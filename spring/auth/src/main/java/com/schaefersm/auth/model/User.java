package com.schaefersm.auth.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Entity
@Document(collection = "springusers")
public class User {

    @Id
    private String _id;


    private String name;

    private String email;

    private LocalDate creationDate = LocalDate.now();

    private String password;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {

    }
}
