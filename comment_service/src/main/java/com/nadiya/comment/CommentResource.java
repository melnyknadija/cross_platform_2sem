package com.nadiya.comment;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.*;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

    @Inject
    @RestClient
    ProductClient productClient;

    private final List<String> phrases = List.of(
            "Якість на висоті",
            "Не рекомендую",
            "Найкраща ціна",
            "Швидка доставка"
    );

    @GET
    public List<CommentDTO> getComments() {
        // Виклик Product Service через HTTP
        List<ProductDTO> products = productClient.getAllProducts();

        List<CommentDTO> comments = new ArrayList<>();
        Random random = new Random();

        for (ProductDTO product : products) {
            // Створюємо об'єкт коментаря, використовуючи дані з ProductDTO
            String randomText = phrases.get(random.nextInt(phrases.size()));
            comments.add(new CommentDTO(product.id(), product.name(), randomText));
        }

        return comments;
    }
}