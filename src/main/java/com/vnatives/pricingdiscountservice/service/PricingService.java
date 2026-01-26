package com.vnatives.pricingdiscountservice.service;

import com.vnatives.vnatives_common_sdk.dto.request.CreateBasePriceRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.request.CreatePricingRuleRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.request.PriceResolveRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.response.PriceResolveResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface PricingService {
    public void saveBasePrice(CreateBasePriceRequestDTO request);
    public void createRule(CreatePricingRuleRequestDTO request);
    public PriceResolveResponseDTO resolvePrice(PriceResolveRequestDTO request);
    public void clearPricingCache();
}
