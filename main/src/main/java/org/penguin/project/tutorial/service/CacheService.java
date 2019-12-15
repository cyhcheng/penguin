package org.penguin.project.tutorial.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
@AllArgsConstructor
public class CacheService {

    private final CountDownLatch countDownLatch;

    @Cacheable(cacheNames = "myCache")
    public String cacheThis(){
        log.info("Terry -> {{}}", countDownLatch);
        log.info("Returning NOT from cache!");
        return "this Is it";
    }

    @Cacheable(cacheNames = "myCache", key = "'myPrefix_'.concat(#relevant)")
    public String cacheThis(String relevant, String unrelevantTrackingId){
        log.info("Returning NOT from cache. Tracking: {}!", unrelevantTrackingId);
        return "this Is it";
    }

    @CacheEvict(cacheNames = "myCache", key = "'myPrefix_'.concat(#relevant)")
    public void forgetAboutThis(String relevant){
        log.info("Forgetting everything about this '{}'!", relevant);
    }
}
