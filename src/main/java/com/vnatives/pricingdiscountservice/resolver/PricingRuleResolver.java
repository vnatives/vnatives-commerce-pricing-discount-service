package com.vnatives.pricingdiscountservice.resolver;


import com.vnatives.pricingdiscountservice.entity.PricingRule;

import java.util.Comparator;
import java.util.List;

public class PricingRuleResolver {

    public static PricingRule resolve(List<PricingRule> rules) {
        return rules.stream()
                .max(Comparator.comparingInt(PricingRule::getPriority))
                .orElse(null);
    }
}
