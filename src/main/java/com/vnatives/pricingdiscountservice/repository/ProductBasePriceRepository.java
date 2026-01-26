package com.vnatives.pricingdiscountservice.repository;

import com.vnatives.pricingdiscountservice.entity.ProductBasePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductBasePriceRepository extends JpaRepository<ProductBasePrice, Long> {

    Optional<ProductBasePrice> findByShopIdAndProductIdAndVariantIdAndActiveTrue(Long shopId, Long productId, Long variantId);
}
