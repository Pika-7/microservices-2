package com.app.orderservice.exception;


import com.app.orderservice.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ApiResponseExceptionHandler {
    @ExceptionHandler(GlobalExceptionHandler.class)
    public ResponseEntity<ApiResponseDto> apiResponse(GlobalExceptionHandler exceptionHandler) {
        return ResponseEntity.badRequest().body(ApiResponseDto.builder()
                        .message(exceptionHandler.getMessage())
                        .success(false)
                .build());
    }
}
