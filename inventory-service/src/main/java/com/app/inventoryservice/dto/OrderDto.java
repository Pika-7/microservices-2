package com.app.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private String orderId;
    private String productId;
    private int quantity;
    private String color;
    private Instant orderTime;
    private BigDecimal totalPrice;
}
