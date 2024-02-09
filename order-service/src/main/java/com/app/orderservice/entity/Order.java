package com.app.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "t_order")
public class Order {
    @Id
    private String orderId;
    private String productId;
    private int quantity;
    private String color;
    private Instant orderTime;
    private BigDecimal totalPrice;
}
