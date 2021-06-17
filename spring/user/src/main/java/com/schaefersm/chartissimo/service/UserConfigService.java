package com.schaefersm.chartissimo.service;

import java.util.HashMap;

import com.schaefersm.chartissimo.model.UserConfig;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserConfigService {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public UserConfig findByUserId(ObjectId userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(userId));
        UserConfig userConfig = mongoTemplate.findOne(query, UserConfig.class);
        return userConfig;
    }

    public HashMap <String, Object> getUserConfig (ObjectId userId) {
        UserConfig userConfig = findByUserId(userId);
        if (userConfig != null) {
            return userConfig.getConfig();
        } else {
            return null;
        }
    }

    public UserConfig updateUserConfig(ObjectId userId, HashMap<String, HashMap<String, Object>> body) {
        HashMap<String, Object> userConfig = body.get("config");
        UserConfig userconfig = findByUserId(userId);
        if (userconfig == null || userConfig == null) {
            return null;
        }
        HashMap<String, Object> currentConfig = userconfig.getConfig();
        userConfig.forEach(
            (key, value)
                -> {
                    currentConfig.put(key, value);
                }
        );
        userconfig.setConfig(currentConfig);
        UserConfig updatedUserConfig = mongoTemplate.save(userconfig);
        if (updatedUserConfig != null) {
            System.out.println("Updated User Config");
            return updatedUserConfig;
        } else {
            return null;
        }
    }

    public UserConfig createUserConfig(ObjectId userId, HashMap<String, HashMap<String, Object>> body) {
        HashMap<String, Object> userConfig = body.get("config");       
        if (userConfig == null) {
            return null;
        };
        UserConfig configExists = findByUserId(userId);
        if (configExists == null) {
            try {
                UserConfig userConfigObject = new UserConfig(userConfig, userId);
                UserConfig newUserConfig = mongoTemplate.insert(userConfigObject);
                if (newUserConfig != null) {
                    System.out.println("Created User Config");
                    return newUserConfig;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                UserConfig updatedUserConfig = updateUserConfig(userId, body);
                return updatedUserConfig;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
