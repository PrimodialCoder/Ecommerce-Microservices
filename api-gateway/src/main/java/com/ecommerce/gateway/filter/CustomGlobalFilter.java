package com.ecommerce.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class CustomGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange, //this has everything about the request and response
            GatewayFilterChain chain
    ) {
        System.out.println("Global Filter : request intercepted : " + exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> System.out.println("Global Filter : response sent : " + exchange.getResponse().getStatusCode()))); //this will pass the request to the next filter in the chain
    }
}
