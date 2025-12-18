package com.nadiya.sales;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;
import io.quarkus.grpc.GrpcClient;


@Path("/sales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalesResource {

    @Inject
    SalesRepository salesRepository;

    @GrpcClient("product-service")
    com.nadiya.grpc.ProductGrpc productClient;

    @GET
    public List<Sale> getAll() {
        return salesRepository.listAll();
    }

    @POST
    @Transactional
    public Sale create(SaleRecord record) {
            Sale entity = new Sale();
            entity.setProductName(record.productName());
            entity.setAmount(record.amount());
            entity.setSaleDate(LocalDateTime.now());
            entity.setType(record.type());

            salesRepository.persist(entity);
            return entity;
        }


    @GET
    @Path("/{id}")
    public Sale getById(@PathParam("id") Long id) {
        Sale entity = salesRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Sale update(@PathParam("id") Long id, SaleRecord record) {
        Sale entity = salesRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.setProductName(record.productName());
        entity.setAmount(record.amount());
        entity.setType(record.type());
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        boolean deleted = salesRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
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