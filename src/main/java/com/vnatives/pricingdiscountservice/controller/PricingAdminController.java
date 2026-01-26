package com.vnatives.pricingdiscountservice.controller;

import com.vnatives.pricingdiscountservice.entity.DiscountType;
import com.vnatives.pricingdiscountservice.entity.PricingRule;
import com.vnatives.pricingdiscountservice.entity.ProductBasePrice;
import com.vnatives.pricingdiscountservice.entity.RuleType;
import com.vnatives.pricingdiscountservice.repository.PricingRuleRepository;
import com.vnatives.pricingdiscountservice.repository.ProductBasePriceRepository;
import com.vnatives.pricingdiscountservice.service.PricingService;
import com.vnatives.vnatives_common_sdk.dto.request.CreateBasePriceRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.request.CreatePricingRuleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingAdminController {

    private final ProductBasePriceRepository basePriceRepo;
    private final PricingRuleRepository ruleRepo;
    private final PricingService pricingService;

    @PostMapping("/base")
    public ResponseEntity<Void> createBasePrice(@RequestBody CreateBasePriceRequestDTO request) {
        pricingService.saveBasePrice(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/rules")
    public ResponseEntity<Void>  createRule(@RequestBody CreatePricingRuleRequestDTO request) {
        pricingService.createRule(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/clear-cache")
    public ResponseEntity<Void> clearPricingCache() {
        pricingService.clearPricingCache();
        return ResponseEntity.ok().build();
    }

}
