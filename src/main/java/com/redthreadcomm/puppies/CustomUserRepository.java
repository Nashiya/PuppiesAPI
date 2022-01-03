package com.redthreadcomm.puppies;

import javafx.geometry.Pos;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;

public interface CustomUserRepository {
    void partialUserUpdate(final String userId, final String fieldName, final Object fieldValue);

   User findByPostId(final String postId);

   List<Post> findByUserId(final String userId);
}
