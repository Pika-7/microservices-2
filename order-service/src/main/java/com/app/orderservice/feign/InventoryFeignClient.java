package com.app.orderservice.feign;

import com.app.orderservice.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${inventory.service.name}", path = "${inventory.service.path}")
public interface InventoryFeignClient {
    @GetMapping("/product-id/{id}")
    ResponseEntity<InventoryDto> getInventoryForProduct(@PathVariable("id") String productId);
}
