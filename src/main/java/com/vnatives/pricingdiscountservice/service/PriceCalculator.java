package com.vnatives.pricingdiscountservice.service;

import com.vnatives.pricingdiscountservice.entity.DiscountType;
import com.vnatives.pricingdiscountservice.entity.PricingRule;

import java.math.BigDecimal;

public class PriceCalculator {

    public static BigDecimal calculateDiscount( BigDecimal basePrice, PricingRule rule) {

        if (rule == null) return BigDecimal.ZERO;

        if (rule.getDiscountType() == DiscountType.PERCENTAGE) {
            return basePrice
                    .multiply(rule.getDiscountValue())
                    .divide(BigDecimal.valueOf(100));
        }
        return rule.getDiscountValue();
    }

}
