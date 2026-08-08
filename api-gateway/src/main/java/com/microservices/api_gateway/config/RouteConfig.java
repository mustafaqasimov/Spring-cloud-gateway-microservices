package com.microservices.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;

@Configuration
public class RouteConfig {

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
            String ip = "unknown";

            if (remoteAddress != null) {
                InetAddress address = remoteAddress.getAddress();
                if (address != null) {
                    ip = address.getHostAddress();
                } else if (remoteAddress.getHostString() != null) {
                    ip = remoteAddress.getHostString();
                }
            }

            return Mono.just(ip);
        };
    }

    @Bean
    public RedisRateLimiter authRateLimiter() {
        return new RedisRateLimiter(2, 5, 1);
    }

    @Primary
    @Bean
    public RedisRateLimiter defaultRateLimiter() {
        return new RedisRateLimiter(20, 40, 1);
    }

    @Bean
    public RouteLocator customRouteLocator(
            RouteLocatorBuilder builder,
            KeyResolver ipKeyResolver,
            RedisRateLimiter authRateLimiter,
            RedisRateLimiter defaultRateLimiter,
            Environment env
    ) {
        String authServiceUrl = env.getProperty("AUTH_SERVICE_URL", "http://localhost:8081");
        String profileServiceUrl = env.getProperty("PROFILE_SERVICE_URL", "http://localhost:8082");
        String feedbackServiceUrl = env.getProperty("FEEDBACK_SERVICE_URL", "http://localhost:8083");

        return builder.routes()
                .route("auth-service-login", r -> r
                        .path("/auth/login", "/auth/register")
                        .filters(f -> f.requestRateLimiter(c -> c
                                .setRateLimiter(authRateLimiter)
                                .setKeyResolver(ipKeyResolver)))
                        .uri(authServiceUrl))

                .route("auth-service-other", r -> r
                        .path("/auth/**")
                        .uri(authServiceUrl))

                .route("profile-service", r -> r
                        .path("/profiles/**")
                        .filters(f -> f.requestRateLimiter(c -> c
                                .setRateLimiter(defaultRateLimiter)
                                .setKeyResolver(ipKeyResolver)))
                        .uri(profileServiceUrl))

                .route("feedback-service", r -> r
                        .path("/feedback/**")
                        .filters(f -> f.requestRateLimiter(c -> c
                                .setRateLimiter(defaultRateLimiter)
                                .setKeyResolver(ipKeyResolver)))
                        .uri(feedbackServiceUrl))

                .build();
    }
}
