package com.schaefersm.gateway.filter;

import com.schaefersm.gateway.exception.JwtTokenMalformedException;
import com.schaefersm.gateway.exception.JwtTokenMissingException;
import com.schaefersm.gateway.util.JwtUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Log
@Component
public class JwtFilter implements GatewayFilter {

    @Value("${cookieName.access}")
    private String accessCookieName;

    @Value("${cookieName.refresh}")
    private String refreshCookieName;

    private JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final List<String> apiEndpoints = List.of("/register", "/login", "/api/data", "/refresh");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(request)) {
            if (request.getCookies().get(accessCookieName) != null) {
                try {
                    log.info("validate access!");
                    String token = request.getCookies().get(accessCookieName).get(0).getValue();
                    jwtUtil.validateAccessToken(token);
                    return chain.filter(exchange);
                } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                    log.info("Invalid access token! " + e.getMessage());
                    return validateRefreshToken(exchange, request);
                }
            } else {
                return validateRefreshToken(exchange, request);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> validateRefreshToken(ServerWebExchange exchange, ServerHttpRequest request) {
        try {
            log.info("Validate refresh!");
            String token = request.getCookies().get(refreshCookieName).get(0).getValue();
            jwtUtil.validateRefreshToken(token);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
            log.info("Invalid refresh token! " + e.getMessage());
            log.info("Denying user!");
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
    }
}