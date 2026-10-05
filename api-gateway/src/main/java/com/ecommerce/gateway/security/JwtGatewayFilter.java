package com.ecommerce.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class JwtGatewayFilter implements GatewayFilter, Ordered {


    private JwtUtil jwtUtil;

    public JwtGatewayFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private static final List<String> PUBLIC_URLS = List.of("/api/users/login",
            "/api/users/register");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        if(PUBLIC_URLS.stream().anyMatch(path::equals)) {
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        if(!jwtUtil.validateToken(token)) {
            return unauthorized(exchange);
        }
        String role = jwtUtil.getRoleFromToken(token);

        if(!isAuthorized(role, method, path)) {
            return forbidden(exchange);
        }

        String userId = jwtUtil.getUserIdFromToken(token);

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());

    }

    //this is the function to send the response as unauthorized
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private static final Map<String, Map<String, List<String>>> ROLE_API_PERMISSIONS = Map.of(
            "ADMIN", Map.of(
                    "GET", List.of("/api/products"),
                    "POST", List.of("/api/products"),
                    "PUT", List.of("/api/products"),
                    "DELETE", List.of("/api/products")
            ),
            "USER", Map.of(
                    "GET", List.of("/api/products")
            )
    );

    public boolean isAuthorized(String role, String method, String path) {
        Map<String, List<String>> rolePermissions = ROLE_API_PERMISSIONS.get(role);
        if (rolePermissions == null || rolePermissions.get(method) == null) {return false;}

        List<String> allowedPaths = rolePermissions.get(method);
        if(allowedPaths == null) {return false;}

        return allowedPaths.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

}
