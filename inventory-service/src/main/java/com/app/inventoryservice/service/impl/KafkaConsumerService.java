package com.app.inventoryservice.service.impl;

import com.app.inventoryservice.dto.ApiResponseDto;
import com.app.inventoryservice.dto.ProductDto;
import com.app.inventoryservice.entity.Inventory;
import com.app.inventoryservice.exception.GlobalExceptionHandler;
import com.app.inventoryservice.feign.ProductFeignClient;
import com.app.inventoryservice.repository.InventoryRepository;
import com.app.orderservice.dto.OrderDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final ProductFeignClient productFeignClient;
    private final InventoryRepository inventoryRepository;

    @KafkaListener(topics = "order-service-topic", groupId = "inventory-service-group")
    public ApiResponseDto updateInventoryOnOrder(@NotNull OrderDto orderDto) {
        log.info("Inside updateInventoryOnOrder method of InventoryServiceImpl");
        log.info("{}", orderDto);
        ProductDto productDto = productFeignClient.getProductById(orderDto.getProductId()).getBody();
        if (productDto != null) {
            Inventory inventory = inventoryRepository.findByProduct(productDto)
                    .orElseThrow(() -> new GlobalExceptionHandler("Product not found"));
            inventory.setQuantity(inventory.getQuantity() - orderDto.getQuantity());
            inventoryRepository.save(inventory);
            return ApiResponseDto.builder()
                    .message("Inventory Updated Successfully")
                    .success(true)
                    .build();
        } else {
            throw new GlobalExceptionHandler("Product not found");
        }
    }
}
