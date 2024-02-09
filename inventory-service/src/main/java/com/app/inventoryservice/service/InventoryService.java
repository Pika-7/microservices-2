package com.app.inventoryservice.service;

import com.app.inventoryservice.dto.ApiResponseDto;
import com.app.inventoryservice.dto.InventoryDto;

public interface InventoryService {
    ApiResponseDto addInventory(InventoryDto inventoryDto);

    InventoryDto getInventoryByProductId(String productId);

    ApiResponseDto updateInventory(InventoryDto inventoryDto, String inventoryId);

    ApiResponseDto removeInventory(String inventoryId);
}
