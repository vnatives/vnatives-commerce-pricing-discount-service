package com.vnatives.pricingdiscountservice.controller;

import com.vnatives.pricingdiscountservice.service.PricingService;
import com.vnatives.vnatives_common_sdk.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/int/v1/api/pricing/cache")
@RequiredArgsConstructor
@Slf4j
public class CacheController {

    private final PricingService pricingService;

    @PutMapping("/clear")
    public ResponseEntity<ApiResponse<Boolean>> clearPricingCache() {
        log.debug("Clearing cache started");
        pricingService.clearPricingCache();
        log.debug("Clearing cache finished");
        return new ResponseEntity(
                ApiResponse.success(Boolean.TRUE, "Cache cleared successfully"),
                HttpStatus.OK);
    }

}
