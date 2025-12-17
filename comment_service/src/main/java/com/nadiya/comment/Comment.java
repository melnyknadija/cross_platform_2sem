package com.nadiya.comment;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.util.List;

@MongoEntity(collection = "comments")
public class Comment extends PanacheMongoEntity {
    public String productId;
    public String author;
    public String text;

    public static List<Comment> findByProductId(String productId) {
        return list("productId", productId);
    }
}