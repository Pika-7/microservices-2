package com.app.orderservice.service;

import com.app.orderservice.dto.ApiResponseDto;
import com.app.orderservice.dto.OrderDto;

public interface OrderService {
    ApiResponseDto createOrder(OrderDto orderDto);
}
