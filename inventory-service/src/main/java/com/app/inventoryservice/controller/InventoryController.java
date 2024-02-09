package com.app.inventoryservice.controller;

import com.app.inventoryservice.dto.ApiResponseDto;
import com.app.inventoryservice.dto.InventoryDto;
import com.app.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseDto> addInventory(@RequestBody @Valid InventoryDto inventoryDto) {
        return new ResponseEntity<>(inventoryService.addInventory(inventoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/product-id/{id}")
    public ResponseEntity<InventoryDto> getInventoryForProduct(@PathVariable("id") String productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateInventory(@RequestBody InventoryDto inventoryDto, @PathVariable("id") String inventoryId) {
        return ResponseEntity.ok(inventoryService.updateInventory(inventoryDto, inventoryId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> removeInventory(@PathVariable("id") String inventoryId) {
        return ResponseEntity.ok(inventoryService.removeInventory(inventoryId));
    }
}
