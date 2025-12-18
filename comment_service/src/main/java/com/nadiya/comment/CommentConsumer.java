package com.nadiya.comment;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CommentConsumer {

    @Incoming("product-in")
    @Transactional
    public void process(String productId) {
        Comment comment = new Comment();
        comment.productId = productId;
        comment.author = "SystemBot";
        comment.text = "Автоматичний відгук для нового товару";
        comment.persist();
        System.out.println("Generated comment for product: " + productId);
    }
}