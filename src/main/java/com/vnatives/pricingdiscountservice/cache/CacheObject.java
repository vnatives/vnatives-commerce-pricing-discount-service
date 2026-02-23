package com.vnatives.pricingdiscountservice.cache;

import com.vnatives.pricingdiscountservice.entity.PricingRule;
import com.vnatives.pricingdiscountservice.entity.ProductBasePrice;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CacheObject {
    private ProductBasePrice productBasePrice;
    private PricingRule rule;
}
