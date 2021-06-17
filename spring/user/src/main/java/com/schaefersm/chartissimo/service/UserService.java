package com.schaefersm.chartissimo.service;

import com.schaefersm.chartissimo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public User readUserById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
