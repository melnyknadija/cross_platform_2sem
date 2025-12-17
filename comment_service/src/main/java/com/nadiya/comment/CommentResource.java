package com.nadiya.comment;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Random;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

    @GET
    public List<Comment> getAll() {
        return Comment.listAll();
    }

    @Inject
    @RestClient
    ProductClient productClient; // Ваш існуючий інтерфейс REST клієнта
    private final List<String> fakeReviews = List.of(
            "Якість перевершила очікування",
            "Непогано за такі гроші",
            "Доставили швидко, все працює",
            "Рекомендую всім!",
            "Трохи не те, що на фото, але ок"
    );

    @POST
    @Path("/generate-for-all")
    public List<Comment> generateReviews() {
        // 1. Отримуємо список товарів через HTTP з іншого мікросервісу
        List<ProductDTO> products = productClient.getAllProducts();
        Random random = new Random();

        // 2. Для кожного товару створюємо коментар і зберігаємо в MongoDB
        for (ProductDTO product : products) {
            Comment comment = new Comment();
            comment.productId = String.valueOf(product.id()); // ID з іншого сервісу
            comment.author = "AutoBot";
            comment.text = fakeReviews.get(random.nextInt(fakeReviews.size()));
            comment.persist(); // Active Record збереження
        }

        return Comment.listAll();
    }

    @POST
    public Response addComment(Comment comment) {
        comment.persist();
        return Response.status(201).entity(comment).build();
    }

    @GET
    @Path("/product/{productId}")
    public List<Comment> getByProduct(@PathParam("productId") String productId) {
        return Comment.findByProductId(productId);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        Comment entity = Comment.findById(new ObjectId(id));
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}