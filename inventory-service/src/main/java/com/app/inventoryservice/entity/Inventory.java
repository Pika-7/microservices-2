package com.app.inventoryservice.entity;

import com.app.inventoryservice.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "t_inventory")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Inventory {
    @Id
    private String inventoryId;
    private ProductDto product;
    private int quantity;
    private String color;
    private Date createdDate;
    private Date lastModifiedDate;
    private InventoryStatus status;
}
