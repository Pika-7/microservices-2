package com.app.inventoryservice.repository;

import com.app.inventoryservice.dto.ProductDto;
import com.app.inventoryservice.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findByProduct(ProductDto productDto);
}
