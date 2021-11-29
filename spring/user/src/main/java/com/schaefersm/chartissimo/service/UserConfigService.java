package com.schaefersm.chartissimo.service;

import java.util.HashMap;

import com.schaefersm.chartissimo.exception.UserConfigNotFoundException;
import com.schaefersm.chartissimo.model.Config;
import com.schaefersm.chartissimo.model.UserConfig;

import com.schaefersm.chartissimo.repository.UserConfigRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserConfigService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserConfigService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public UserConfig findByUserId(ObjectId userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(userId));
        UserConfig userConfig = mongoTemplate.findOne(query, UserConfig.class);
        return userConfig;
    }

    public UserConfig getUserConfig(ObjectId userId) {
        UserConfig userConfig = findByUserId(userId);
        if (userConfig == null) {
            throw new UserConfigNotFoundException();
        }
        return userConfig;
    }

    public void createUserConfig(ObjectId userId, Config config) {
        UserConfig userConfig = findByUserId(userId);
        if (userConfig == null) {
            UserConfig newUserConfig = new UserConfig(config, userId);
            mongoTemplate.insert(newUserConfig);
        } else {
            userConfig.setConfig(config);
            mongoTemplate.save(userConfig);
        }
    }
}
