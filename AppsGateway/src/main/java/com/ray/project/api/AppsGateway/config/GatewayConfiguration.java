package com.ray.project.api.AppsGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("food_getRandomRecipes_route", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/lifestyles-api/food/getRandomRecipes")
                        .uri("http://localhost:8085"))
                .route("food_getNutrientById_route", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/lifestyles-api/food/getNutrientById/{id}")
                        .uri("http://localhost:8085"))
                .route("football_getLeagueStandingsBySeason_route", r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path("/lifestyles-api/football/getLeagueStandingsBySeason")
                        .uri("http://localhost:8085"))
                .route("register_route", r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path("/lifestyles-api/register")
                        .uri("http://localhost:8085"))
                .route("generateToken_route", r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path("/lifestyles-api/generateToken")
                        .uri("http://localhost:8085"))
                .build();
    }
}
