package org.penguin.project.tutorial;

import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.service.CacheService;
import org.penguin.project.tutorial.service.ControllerCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Slf4j
@EnableCaching
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Autowired
    CacheService cacheService;

    @Autowired
    ControllerCacheService controllerCacheService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String firstString = cacheService.cacheThis();
        log.info("Terry -> First: {}", firstString);
        String secondString = cacheService.cacheThis();
        log.info("Terry -> Second: {}", secondString);
    }

    private String getFromControlledCache(String param) {
        String fromCache = controllerCacheService.getFromCache(param);
        if (fromCache == null) {
            log.info("Oups - Cache was empty. Going to populate it");
            String newValue = controllerCacheService.populateCache(param, UUID.randomUUID().toString());
            log.info("Populated Cache with: {}", newValue);
            return newValue;
        }
        log.info("Returning from Cache: {}", fromCache);
        return fromCache;
    }

    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(1);
    }
}
