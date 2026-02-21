package com.vnatives.pricingdiscountservice.controller;

import com.vnatives.pricingdiscountservice.entity.DiscountType;
import com.vnatives.pricingdiscountservice.entity.PricingRule;
import com.vnatives.pricingdiscountservice.entity.ProductBasePrice;
import com.vnatives.pricingdiscountservice.entity.RuleType;
import com.vnatives.pricingdiscountservice.repository.PricingRuleRepository;
import com.vnatives.pricingdiscountservice.repository.ProductBasePriceRepository;
import com.vnatives.pricingdiscountservice.service.PricingService;
import com.vnatives.vnatives_common_sdk.dto.ApiResponse;
import com.vnatives.vnatives_common_sdk.dto.request.pricing.CreateBasePriceRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.request.pricing.CreatePricingRuleRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/int/v1/api/pricing-admin")
@RequiredArgsConstructor
@Slf4j
public class PricingAdminController {

    private final PricingService pricingService;

    @PostMapping("/base")
    public ResponseEntity<ApiResponse<Boolean>> createBasePrice(@RequestBody CreateBasePriceRequestDTO request) {
        log.info("PricingAdminController.createBasePrice started");
        pricingService.saveBasePrice(request);
        log.info("PricingAdminController.createBasePrice finished");
        return new ResponseEntity<>(
                ApiResponse.success(Boolean.TRUE, "Base price has been created successfully"),
                HttpStatus.CREATED);
    }

    @PostMapping("/rules")
    public ResponseEntity<ApiResponse<Boolean>>  createRule(@RequestBody CreatePricingRuleRequestDTO request) {
        log.info("PricingAdminController.createRule started");
        pricingService.createRule(request);
        log.info("PricingAdminController.createRule finished");
        return new ResponseEntity<>(
                ApiResponse.success(Boolean.TRUE, "Rule created successfully"),
                HttpStatus.CREATED);
    }

}
