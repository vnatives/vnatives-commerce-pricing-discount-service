package com.vnatives.pricingdiscountservice.controller;

import com.vnatives.pricingdiscountservice.service.PricingService;
import com.vnatives.vnatives_common_sdk.dto.ApiResponse;
import com.vnatives.vnatives_common_sdk.dto.request.pricing.PriceResolveRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.response.pricing.PriceResolveResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/int/v1/api/pricing")
@RequiredArgsConstructor
@Slf4j
public class PricingResolveController {

    private final PricingService pricingService;

    @PostMapping("/resolve")
    public ResponseEntity<ApiResponse<PriceResolveResponseDTO>> resolvePrice(
            @RequestBody PriceResolveRequestDTO request) {
        log.info("Resolving price request {}", request);
        PriceResolveResponseDTO priceResolveResponseDTO = pricingService.resolvePrice(request);
        log.info("Resolved price response {}", priceResolveResponseDTO);
        return new ResponseEntity<>(
                ApiResponse.success(priceResolveResponseDTO, "Price resolved successfully."),
                HttpStatus.OK
        );
    }
}
