package com.vnatives.pricingdiscountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.class
        }
)
public class VnativesCommercePricingDiscountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VnativesCommercePricingDiscountServiceApplication.class, args);
	}

}