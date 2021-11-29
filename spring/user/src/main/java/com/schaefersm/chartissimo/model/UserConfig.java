package com.schaefersm.chartissimo.model;

import java.util.HashMap;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "userconfigs")
public class UserConfig {

    @Id
    private String _id;
    private ObjectId user;
    private Config config;

    public UserConfig(Config config, ObjectId user) {
        this.config = config;
        this.user = user;
    }

}