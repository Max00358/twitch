package com.laioffer.twitch;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Paths;

// Here are the most common annotations that make a class/interface a Spring bean:

// Stereotype Annotations:
    // @Component: A generic stereotype for any Spring-managed component
    // @Service: Indicates that the class is a service-layer component (e.g., TwitchService)
    // @Repository: Indicates that the class is a data access object (DAO) for interacting with a database
    // @Controller: Indicates that the class is a web controller in Spring MVC
    // @Configuration: Indicates that the class is a configuration class that defines beans

// Specialized Annotations:
    // @FeignClient: Creates a Feign client bean for making HTTP requests to a remote API
    // @Bean: Used in configuration classes to explicitly define a bean (e.g., in a @Configuration class)

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class TwitchApplication {
    public static void main(String[] args) {
        // this allows us to reference .env variable in .yml file as such:
            // client-id: ${TWITCH_CLIENT_ID}

        // running locally
        if (System.getenv("AWS_APPRUNNER") == null) {
            loadLocalEnv();
        }
        SpringApplication.run(TwitchApplication.class, args);
    }

    private static void loadLocalEnv() {
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .ignoreIfMissing()
                .load();

        // Set system properties for Spring to read
        System.setProperty("TWITCH_CLIENT_ID", dotenv.get("TWITCH_CLIENT_ID"));
        System.setProperty("TWITCH_CLIENT_SECRET", dotenv.get("TWITCH_CLIENT_SECRET"));
        System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
    }
}