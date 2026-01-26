package com.vnatives.pricingdiscountservice.repository;

import com.vnatives.pricingdiscountservice.entity.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    List<PricingRule> findByShopIdAndActiveTrue(Long shopId);
}
