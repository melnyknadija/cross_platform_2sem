package com.nadiya.sales;

import java.time.LocalDateTime;

public record SaleRecord(
        Long id,
        String productName,
        double amount,
        LocalDateTime saleDate,
        String type
) {}