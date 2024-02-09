package com.app.inventoryservice.dto;

import com.app.inventoryservice.entity.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {
    private String inventoryId;
    private String productId;
    private int quantity;
    private String color;
    private Date createdDate;
    private Date lastModifiedDate;
    private InventoryStatus status;
}
