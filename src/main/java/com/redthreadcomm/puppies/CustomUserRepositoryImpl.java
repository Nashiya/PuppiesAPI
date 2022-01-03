package com.redthreadcomm.puppies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CustomUserRepositoryImpl implements CustomUserRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void partialUserUpdate(String userId, String fieldName, Object fieldValue) {
        mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("userId").is(userId)),
                BasicUpdate.update(fieldName,fieldValue), FindAndModifyOptions.none(),User.class);
    }

    @Override
    public User findByPostId(String postId) {
        List<Post> posts = new ArrayList<>();
        Criteria elementMatchCriteria = Criteria.where("posts").elemMatch(Criteria.where("postId").is(postId));
        Query query = Query.query(elementMatchCriteria);
        query.fields().position("posts", 1);
         User user =mongoTemplate.findOne(query, User.class);
       return user;
    }

    @Override
    public List<Post> findByUserId(String userId) {
        User user = new User();
        Criteria elementMatchCriteria = Criteria.where("posts.likedUsers").elemMatch(Criteria.where("_id").is(userId));
        Query query = Query.query(elementMatchCriteria);
        query.fields().position("posts", 1);
        List<Post> p = mongoTemplate.find(query, Post.class);

        return p;
    }

}
