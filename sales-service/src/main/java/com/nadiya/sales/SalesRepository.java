package com.nadiya.sales;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SalesRepository implements PanacheRepository<Sale> {
    // Methods persist(), listAll(), findById() were iherited
}