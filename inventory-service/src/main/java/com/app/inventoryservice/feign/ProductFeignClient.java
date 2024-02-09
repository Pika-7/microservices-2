package com.app.inventoryservice.feign;

import com.app.inventoryservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${product.service.name}", path = "${product.service.path}")
public interface ProductFeignClient {
    @GetMapping("/by-id/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable("id") String productId);
}
