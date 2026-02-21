package com.vnatives.pricingdiscountservice.exception;

import com.vnatives.vnatives_common_sdk.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BasePriceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleBasePriceNotFoundException(BasePriceNotFoundException ex){
        log.error("handleBasePriceNotFoundException ", ex);
        return new ResponseEntity<>(
          ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.toString()),
          HttpStatus.NOT_FOUND
        );
    }

}
