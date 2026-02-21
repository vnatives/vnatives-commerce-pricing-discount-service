package com.vnatives.pricingdiscountservice.service.impl;

import com.vnatives.pricingdiscountservice.cache.PricingCache;
import com.vnatives.pricingdiscountservice.entity.PricingRule;
import com.vnatives.pricingdiscountservice.entity.ProductBasePrice;
import com.vnatives.pricingdiscountservice.exception.BasePriceNotFoundException;
import com.vnatives.pricingdiscountservice.mapper.PriceMapper;
import com.vnatives.pricingdiscountservice.repository.PricingRuleRepository;
import com.vnatives.pricingdiscountservice.repository.ProductBasePriceRepository;
import com.vnatives.pricingdiscountservice.resolver.PricingRuleResolver;
import com.vnatives.pricingdiscountservice.rule.PricingRuleFilter;
import com.vnatives.pricingdiscountservice.service.PriceCalculator;
import com.vnatives.pricingdiscountservice.service.PricingService;
import com.vnatives.vnatives_common_sdk.dto.request.pricing.CreateBasePriceRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.request.pricing.CreatePricingRuleRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.request.pricing.PriceResolveRequestDTO;
import com.vnatives.vnatives_common_sdk.dto.response.pricing.PriceResolveResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingServiceImpl implements PricingService {

    private final ProductBasePriceRepository basePriceRepo;

    private final PricingRuleRepository ruleRepo;

    private final PricingCache cache;

    @Override
    public void saveBasePrice(CreateBasePriceRequestDTO request) {
        log.info("Save Base Price Request {}", request);
        ProductBasePrice entity = PriceMapper.toEntity(request);
        basePriceRepo.save(entity);
        log.info("Save Base Price Success {}", entity);
    }

    @Override
    public void createRule(CreatePricingRuleRequestDTO request) {
        log.info("Inside PricingServiceImpl createRule with request {}", request);
        PricingRule entity = PriceMapper.toEntity(request);
        ruleRepo.save(entity);
        log.info("Create PricingRule Success {}", entity);
    }

    public PriceResolveResponseDTO resolvePrice(PriceResolveRequestDTO request) {
        log.info("Inside PricingServiceImpl resolvePrice request {}", request);
        String cacheKey = PricingCache.getKey(request.getShopId(),  request.getProductId(), request.getProductVariantId());

        Optional<PriceResolveResponseDTO> cacheOptional = cache.get(cacheKey);
        if(cacheOptional.isPresent()) {
            log.info("Get from cache for {}", cacheKey);
            return cacheOptional.get();
        }

        ProductBasePrice basePrice = basePriceRepo
                .findByShopIdAndProductIdAndProductVariantIdAndActiveTrue(
                        request.getShopId(),
                        request.getProductId(),
                        request.getProductVariantId())
                .orElseThrow(() -> new BasePriceNotFoundException(request.getShopId(),  request.getProductId(), request.getProductVariantId()));

        List<PricingRule> rules = ruleRepo.findByShopIdAndActiveTrue(request.getShopId());

        List<PricingRule> applicableRules =
                PricingRuleFilter.filterApplicableRules(
                        rules,
                        request.getProductId(),
                        request.getProductVariantId(),
                        request.getAtTime());

        PricingRule rule = PricingRuleResolver.resolve(applicableRules);

        BigDecimal discount = PriceCalculator.calculateDiscount(
                        basePrice.getBasePrice(), rule);

        BigDecimal finalPrice = basePrice.getBasePrice().subtract(discount);

        PriceResolveResponseDTO response =
                PriceResolveResponseDTO.builder()
                        .basePrice(basePrice.getBasePrice())
                        .discountAmount(discount)
                        .finalPrice(finalPrice)
                        .appliedRuleType(
                                rule != null ? rule.getRuleType().toString() : null)
                        .ruleEndTime(rule.getEndTime())
                        .build();

        cache.put(cacheKey, response);
        log.info("PricingServiceImpl resolvePrice success {}", response);
        return response;

    }

    @Override
    public void clearPricingCache() {
        cache.clear();
    }
}
