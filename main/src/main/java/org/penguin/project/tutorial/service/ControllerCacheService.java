package org.penguin.project.tutorial.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ControllerCacheService {

    private static final String CONTROLLED_PREFIX = "myControlledPrefix_";

    public static String getCacheKey(String relevant) {
        return CONTROLLED_PREFIX + relevant;
    }

    @Cacheable(cacheNames = "myControlledCache", key = "T(org.penguin.project.tutorial.service.ControllerCacheService).getCacheKey(#relevant)")
    public String getFromCache(String relevant) {
        return null;
    }

    @CachePut(cacheNames = "myControlledCache", key = "T(org.penguin.project.tutorial.service.ControllerCacheService).getCacheKey(#relevant)")
    public String populateCache(String relevant, String trackingId) {
        return "this is it again!";
    }

    @CacheEvict(cacheNames = "myControlledCache", key = "T(org.penguin.project.tutorial.service.ControllerCacheService).getCacheKey(#relevant)")
    public void removeFromCache(String relevant) {
    }
}
