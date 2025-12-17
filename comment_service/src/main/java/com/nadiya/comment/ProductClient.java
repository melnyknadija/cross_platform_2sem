package com.nadiya.comment;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "product-api")
@Path("/products")
public interface ProductClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductDTO> getAllProducts();
}