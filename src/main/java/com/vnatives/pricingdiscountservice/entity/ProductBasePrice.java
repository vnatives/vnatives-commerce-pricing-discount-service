package com.vnatives.pricingdiscountservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product_base_price")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBasePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopId;
    private Long productId;
    private Long variantId;

    private BigDecimal basePrice;
    private String currency;

    private boolean active;
}
