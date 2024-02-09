package com.app.inventoryservice.service.impl;

import com.app.inventoryservice.dto.ApiResponseDto;
import com.app.inventoryservice.dto.InventoryDto;
import com.app.inventoryservice.dto.ProductDto;
import com.app.inventoryservice.entity.Inventory;
import com.app.inventoryservice.entity.InventoryStatus;
import com.app.inventoryservice.exception.GlobalExceptionHandler;
import com.app.inventoryservice.feign.ProductFeignClient;
import com.app.inventoryservice.repository.InventoryRepository;
import com.app.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductFeignClient productFeignClient;

    @Override
    public ApiResponseDto addInventory(InventoryDto inventoryDto) {
        log.info("Inside addInventory method of InventoryServiceImpl");
        ProductDto productDtoObj = productFeignClient.getProductById(inventoryDto.getProductId()).getBody();
        if (inventoryRepository.findByProduct(productDtoObj).isPresent()) {
            log.info("Inventory for the product already exists");
        } else {
            log.info("New Inventory persisting");
        }
        assert productDtoObj != null;
        inventoryRepository.save(Inventory.builder()
                .inventoryId(UUID.randomUUID().toString().split("-")[0])
                .product(productDtoObj)
                .color(inventoryDto.getColor())
                .createdDate(new Date())
                .lastModifiedDate(new Date())
                .quantity(inventoryDto.getQuantity())
                .status(InventoryStatus.valueOf(inventoryDto.getStatus().name()))
                .build());
        return ApiResponseDto.builder()
                .message("Inventory Added Successfully")
                .success(true)
                .build();
    }

    @Override
    public InventoryDto getInventoryByProductId(String productId) {
        log.info("Inside getInventoryByProductId method of InventoryServiceImpl");
        ProductDto productDto = productFeignClient.getProductById(productId).getBody();
        if (productDto != null) {
            log.info("Product already exists");
            Inventory inventory = inventoryRepository.findByProduct(productDto).orElseThrow(() -> new GlobalExceptionHandler("Inventory not found"));
            return InventoryDto.builder()
                    .productId(productId)
                    .inventoryId(inventory.getInventoryId())
                    .color(inventory.getColor())
                    .status(InventoryStatus.valueOf(inventory.getStatus().name()))
                    .createdDate(inventory.getCreatedDate())
                    .quantity(inventory.getQuantity())
                    .lastModifiedDate(inventory.getLastModifiedDate())
                    .build();
        } else {
            log.error("Product not found");
            throw new GlobalExceptionHandler("Product Not Found");
        }
    }

    @Override
    public ApiResponseDto updateInventory(InventoryDto inventoryDto, String inventoryId) {
        log.info("Inside updateInventory method of InventoryServiceImpl");
        if (inventoryRepository.existsById(inventoryId)) {
            log.info("Inventory already exists");
            ProductDto productDto = productFeignClient.getProductById(inventoryDto.getProductId()).getBody();
            if (productDto != null) {
                Inventory inventory = Inventory.builder()
                        .inventoryId(inventoryId)
                        .product(productDto)
                        .quantity(inventoryDto.getQuantity())
                        .color(inventoryDto.getColor())
                        .createdDate(inventoryDto.getCreatedDate())
                        .lastModifiedDate(inventoryDto.getLastModifiedDate())
                        .status(InventoryStatus.valueOf(inventoryDto.getStatus().name()))
                        .build();
                inventoryRepository.save(inventory);
                return ApiResponseDto.builder()
                        .message("Inventory Already Exists")
                        .success(true)
                        .build();
            } else {
                log.error("Product Not Found");
                throw new GlobalExceptionHandler("Product Not Found");
            }
        } else {
            log.error("Inventory not found");
            throw new GlobalExceptionHandler("Inventory Not Found");
        }
    }

    @Override
    public ApiResponseDto removeInventory(String inventoryId) {
        log.info("Inside removeInventory method of InventoryServiceImpl");
        if (inventoryRepository.existsById(inventoryId)) {
            inventoryRepository.deleteById(inventoryId);
            return ApiResponseDto.builder()
                    .message("Inventory Deleted successfully")
                    .success(true)
                    .build();
        }
        throw new GlobalExceptionHandler("Inventory Not Found");
    }


}
