package com.wangxin.app.service;

import com.wangxin.app.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 王鑫 on 2016/8/23.
 */
@Service
public class UserService {

    private static String USER_COLLECTION = "user";

    @Autowired
   private  MongoTemplate mongoTemplate;

    /**
     *
     * @param user
     */
    public void saveUser(User user){

        mongoTemplate.save(user, USER_COLLECTION);

    }

//    /**
//     *
//     * @param name
//     * @return
//     */
//    public User findUserByName(String name){
//
//        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), User.class, USER_COLLECTION);
//
//    }

    public List<User> findUserByName(String name){

        return mongoTemplate.find(new Query(Criteria.where("name").is(name)), User.class, USER_COLLECTION);

    }


}
