package com.vnatives.pricingdiscountservice.cache;

import com.vnatives.vnatives_common_sdk.dto.response.PriceResolveResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PricingCache {

    private final RedisTemplate<String, PriceResolveResponseDTO> redisTemplate;

    public static final String REDIS_CACHE_PREFIX = "pricing:";

    public static String getKey(Long shopId, Long productId, Long variantId) {
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
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(key)
        );
    }

    /**
     * Store price with TTL until rule end time
     */
    public void put(String key, PriceResolveResponseDTO response) {

        if (response.getRuleEndTime() == null) {
            // fallback: cache briefly (safety net)
            redisTemplate.opsForValue()
                    .set(key, response, Duration.ofMinutes(5));
            return;
        }

        long ttlSeconds = Duration.between(
                Instant.now(),
                response.getRuleEndTime()
        ).getSeconds();

        if (ttlSeconds <= 0) {
            return; // rule already expired, don't cache
        }

        redisTemplate.opsForValue()
                .set(key, response, ttlSeconds, TimeUnit.SECONDS);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public void clear() {
        Set<String> keys = redisTemplate.keys(REDIS_CACHE_PREFIX+"*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
