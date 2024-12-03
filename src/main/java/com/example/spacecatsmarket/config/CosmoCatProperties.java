package com.example.spacecatsmarket.config;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application")
public class CosmoCatProperties {

    Map<String, CatGreeting> greetings;

    @Data
    @NoArgsConstructor
    public static class CatGreeting {
        String name;
        String message;
    }

}