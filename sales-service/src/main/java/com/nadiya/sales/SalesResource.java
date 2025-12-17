package com.nadiya.sales;

import io.quarkus.grpc.GrpcClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import io.smallrye.mutiny.Uni;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Path("/sales")
@Produces(MediaType.APPLICATION_JSON)
public class SalesResource {

    @GrpcClient("product-service")
    com.nadiya.grpc.ProductGrpc productClient;

    private final Random random = new Random();

    @GET
    @Path("/last-sales")
    public List<SaleRecord> getMockHttpSales() {
        return List.of(
                new SaleRecord(1L, "Static Phone", 500.0, LocalDateTime.now(), "HTTP"),
                new SaleRecord(2L, "Static Laptop", 1200.0, LocalDateTime.now(), "HTTP")
        );
    }

    @GET
    @Path("/from-grpc/{id}")
    public Uni<SaleRecord> getSaleViaGrpc(@PathParam("id") Long id) {
        com.nadiya.grpc.ProductRequest request = com.nadiya.grpc.ProductRequest.newBuilder()
                .setId(id)
                .build();

        return productClient.getProduct(request)
                .onItem().transform(resp -> new SaleRecord(
                        resp.getId(),
                        resp.getName(),
                        resp.getPrice(),
                        LocalDateTime.now(),
                        "GRPC"
                ));
    }
}