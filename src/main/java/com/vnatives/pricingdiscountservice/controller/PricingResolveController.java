package com.vnatives.pricingdiscountservice.controller;

import com.vnatives.pricingdiscountservice.service.PricingService;
import com.vnatives.vnatives_common_sdk.dto.request.PriceResolveRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.response.PriceResolveResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingResolveController {

    private final PricingService pricingService;

    @PostMapping("/resolve")
    public PriceResolveResponseDTO resolvePrice(
            @RequestBody PriceResolveRequestDTO request) {
        return pricingService.resolvePrice(request);
    }
}
