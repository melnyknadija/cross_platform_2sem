package com.nadiya.product;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final List<Product> products = List.of(
            new Product(1L, "Laptop", 1500.0),
            new Product(2L, "Smartphone", 800.0),
            new Product(3L, "Headphones", 150.0)
    );

    @GET
    public List<Product> getAll() {
        return products;
    }

    @GET
    @Path("/{id}")
    public Product getById(@PathParam("id") Long id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new jakarta.ws.rs.NotFoundException());
    }
}