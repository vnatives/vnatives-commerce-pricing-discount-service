package com.vnatives.pricingdiscountservice.mapper;

import com.vnatives.pricingdiscountservice.entity.DiscountType;
import com.vnatives.pricingdiscountservice.entity.PricingRule;
import com.vnatives.pricingdiscountservice.entity.ProductBasePrice;
import com.vnatives.pricingdiscountservice.entity.RuleType;
import com.vnatives.vnatives_common_sdk.dto.request.CreateBasePriceRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.request.CreatePricingRuleRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.response.PriceResolveResponseDTO;

public class PriceMapper {
    public static ProductBasePrice toEntity(CreateBasePriceRequestDTO request) {
        return ProductBasePrice.builder()
                .shopId(request.getShopId())
                .productId(request.getProductId())
                .variantId(request.getVariantId())
                .basePrice(request.getBasePrice())
                .currency(request.getCurrency())
                .active(true)
                .build();
    }

    public static PricingRule toEntity(CreatePricingRuleRequestDTO request) {
        return PricingRule.builder()
                .shopId(request.getShopId())
                .productId(request.getProductId())
                .variantId(request.getVariantId())
                .ruleType(RuleType.valueOf(request.getRuleType()))
                .discountType(DiscountType.valueOf(request.getDiscountType()))
                .discountValue(request.getDiscountValue())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .priority(request.getPriority())
                .active(true)
                .build();
    }

}
