package com.schaefersm.gateway;

import com.schaefersm.gateway.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Autowired
    private JwtFilter filter;

    @Bean
    RouteLocator gateway(RouteLocatorBuilder rlb) {
        return rlb
                .routes()
                .route(routeSpec -> routeSpec
                .path("/auth/login")
                .filters(f -> f.filter(filter))
                .uri("http://localhost:6000"))
                .route(routeSpec -> routeSpec
                .path("/auth/refresh")
                .filters(f -> f.filter(filter))
                .uri("http://localhost:6000"))
                .route(routeSpec -> routeSpec
                .path("/auth/register")
                .filters(f -> f.filter(filter))
                .uri("http://localhost:6000"))
                .route(routeSpec -> routeSpec
                .path("/api/data/**")
                .filters(f -> f.filter(filter))
                .uri("http://localhost:5000"))
                .route(routeSpec -> routeSpec
                .path("/api/user/**")
                .filters(f -> f.filter(filter))
                .uri("http://localhost:6000"))
                .build();
    }


}
