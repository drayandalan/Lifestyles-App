package com.ray.project.api.FootballApp.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfiguration {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS) // Set connect timeout to X seconds
                .readTimeout(120, TimeUnit.SECONDS)    // Set read timeout to X seconds
                .build();
    }
}
