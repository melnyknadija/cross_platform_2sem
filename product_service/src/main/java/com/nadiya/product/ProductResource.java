package com.nadiya.product;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    @Channel("product-created")
    Emitter<String> productEmitter;

    @GET
    public List<Product> listAll() {
        return Product.listAll();
    }

    @POST
    public Response create(Product product) {
        product.persist();
        productEmitter.send(product.id.toString());
        return Response.status(201).entity(product).build();
    }

    @GET
    @Path("/{id}")
    public Product get(@PathParam("id") String id) {
        return Product.findById(new ObjectId(id));
    }

    @PUT
    @Path("/{id}")
    public Product update(@PathParam("id") String id, Product product) {
        Product entity = Product.findById(new ObjectId(id));
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.name = product.name;
        entity.description = product.description;
        entity.price = product.price;
        entity.update();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        Product entity = Product.findById(new ObjectId(id));
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

    @GET
    @Path("/name/{name}")
    public Product get_by_name(@PathParam("name") String name) {
        return Product.findByName(name);
    }
}