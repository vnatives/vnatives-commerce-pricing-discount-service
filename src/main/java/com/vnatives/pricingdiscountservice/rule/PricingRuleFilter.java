package com.vnatives.pricingdiscountservice.rule;

import com.vnatives.pricingdiscountservice.entity.PricingRule;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class PricingRuleFilter {

    public static List<PricingRule> filterApplicableRules(
            List<PricingRule> rules,
            String productId,
            String variantId,
            Instant atTime) {

        return rules.stream()
                .filter(PricingRule::isActive)
                .filter(r ->
                        (r.getProductId() == null || r.getProductId().equals(productId)) &&
                                (r.getProductVariantId() == null || r.getProductVariantId().equals(variantId)) &&
                                !atTime.isBefore(r.getStartTime()) &&
                                !atTime.isAfter(r.getEndTime())
                )
                .collect(Collectors.toList());
    }
}
