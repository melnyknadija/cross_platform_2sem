package com.nadiya.product;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection = "products")
public class Product extends PanacheMongoEntity {
    public String name;
    public String description;
    public double price;

    // Active Record дозволяє використовувати статичні методи для пошуку
    public static Product findByName(String name) {
        return find("name", name).firstResult();
    }
}