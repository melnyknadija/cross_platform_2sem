package com.nadiya.product;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class ProductGrpcService implements com.nadiya.grpc.ProductGrpc {

    @Override
    public Uni<com.nadiya.grpc.ProductResponse> getProduct(com.nadiya.grpc.ProductRequest request) {
        // Повертаємо фіксований результат незалежно від ID у запиті
        return Uni.createFrom().item(
                com.nadiya.grpc.ProductResponse.newBuilder()
                        .setId(999L)
                        .setName("Fake GRPC Item")
                        .setPrice(0.01)
                        .build()
        );
    }
}