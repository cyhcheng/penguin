package org.penguin.project.tutorial;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.penguin.project.tutorial.service.CacheService;
import org.penguin.project.tutorial.service.ControllerCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Slf4j
@EnableCaching
@SpringBootApplication(scanBasePackages = "org.penguin.project.tutorial")
public class MainApplication {

    @Autowired
    CacheService cacheService;

    @Autowired
    ControllerCacheService controllerCacheService;

    @Autowired
    ModelMapper modelMapper;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
        log.info("# Beans: {}", ctx.getBeanDefinitionCount());

        String[] names = ctx.getBeanDefinitionNames();
        Arrays.sort(names);
        Arrays.asList(names).forEach(System.out::println);
    }

    /**
     * 代替implements CommandLineRunner
     *
     * @return
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            String firstString = cacheService.cacheThis();
            log.info("Terry -> First: {}", firstString);
            String secondString = cacheService.cacheThis();
            log.info("Terry -> Second: {}", secondString);
        };
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

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        The number 6 represents how strong you want the encryption.It can be in a range between 4 and 31.
//        If you do not put a number the program will use one randomly each time
//        That you start the application, so your encrypted passwords will not work well
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
