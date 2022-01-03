package com.redthreadcomm.puppies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    /*public Post createPost(String userId, List<Post> posts){
        User user = mongoTemplate.findById(userId,User.class);
        return user;
    }*/
}
