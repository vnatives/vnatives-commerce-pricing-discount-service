package com.vnatives.pricingdiscountservice.cache;

import com.vnatives.vnatives_common_sdk.dto.response.pricing.PriceResolveResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class PricingCache {

    private final RedisTemplate<String, PriceResolveResponseDTO> redisTemplate;

    public static final String REDIS_CACHE_PREFIX = "pricing:";

    public static String getKey(String shopId, String productId, String variantId) {
        return new StringBuilder(REDIS_CACHE_PREFIX)
                .append(shopId+":")
                .append(productId+":")
                .append(variantId)
                .toString();
    }
    /**
     * Get cached price if present
     */
    public Optional<PriceResolveResponseDTO> get(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key));
        } catch (Exception e) {
            log.warn("Redis GET failed for key {}", key, e);
            return Optional.empty();
        }
    }
    /**
     * Store price with TTL until rule end time
     */
    public void put(String key, PriceResolveResponseDTO response) {
        try {
            if (response.getRuleEndTime() == null) {
                redisTemplate.opsForValue().set(key, response, Duration.ofMinutes(5));
                return;
            }

            long ttlSeconds = Duration.between(
                    Instant.now(),
                    response.getRuleEndTime()
            ).getSeconds();

            if (ttlSeconds > 0) {
                redisTemplate.opsForValue().set(key, response, ttlSeconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.warn("Redis PUT failed for key {}", key, e);
        }
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public void clear() {
        Set<String> keys = redisTemplate.keys(REDIS_CACHE_PREFIX+"*");
        if (keys != null && !keys.isEmpty()) {
            log.info("Cache cleared for keys {}", keys);
            redisTemplate.delete(keys);
        }
    }
}
