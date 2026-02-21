package com.vnatives.pricingdiscountservice.exception;

public class BasePriceNotFoundException extends RuntimeException {
    public BasePriceNotFoundException(String shopId, String productId, String productVariantId) {
        super(String.format("Base price not found for shop %s product %s variant %s", shopId, productId, productVariantId));
    }
}
