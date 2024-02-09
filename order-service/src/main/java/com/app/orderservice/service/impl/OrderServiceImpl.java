package com.app.orderservice.service.impl;

import com.app.orderservice.config.KafkaProducerConfig;
import com.app.orderservice.dto.ApiResponseDto;
import com.app.orderservice.dto.InventoryDto;
import com.app.orderservice.dto.OrderDto;
import com.app.orderservice.dto.ProductDto;
import com.app.orderservice.entity.Order;
import com.app.orderservice.exception.GlobalExceptionHandler;
import com.app.orderservice.feign.InventoryFeignClient;
import com.app.orderservice.feign.ProductFeignClient;
import com.app.orderservice.repository.OrderRepository;
import com.app.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducerConfig kafkaProducerConfig;
    private final ModelMapper modelMapper;
    private final ProductFeignClient productFeignClient;
    private final InventoryFeignClient inventoryFeignClient;

    @Override
    public ApiResponseDto createOrder(OrderDto orderDto) {
        log.info("Inside createOrder method of OrderServiceImpl");
        ProductDto productDtoObj = productFeignClient.getProductById(orderDto.getProductId()).getBody();
        InventoryDto inventoryDto = inventoryFeignClient.getInventoryForProduct(orderDto.getProductId()).getBody();
        if (productDtoObj != null && inventoryDto != null) {
            if (orderDto.getQuantity() > inventoryDto.getQuantity()) {
                throw new GlobalExceptionHandler(String.format("Maximum Quantity Available: %s", inventoryDto.getQuantity()));
            }
            orderDto.setOrderId(UUID.randomUUID().toString().split("-")[0]);
            orderDto.setOrderTime(Instant.now());
            orderDto.setTotalPrice(productDtoObj.getProductPrice().multiply(BigDecimal.valueOf(orderDto.getQuantity())));
            orderRepository.save(modelMapper.map(orderDto, Order.class));
            kafkaProducerConfig.sendMessage(orderDto);
            return ApiResponseDto.builder()
                    .message("Order Placed Successfully")
                    .success(true)
                    .build();
        } else {
            return ApiResponseDto.builder()
                    .message("Something Went Wrong")
                    .success(false)
                    .build();
        }
    }
}
