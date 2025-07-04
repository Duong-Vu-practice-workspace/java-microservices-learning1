package vn.edu.ptit.duongvct.practice1.socialmedia.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("blob-service", r -> r
                        .path("/api/v1/storage/**")
                        .filters(f -> f.retry(retryConfig -> retryConfig
                                        .setRetries(10)
                                        .setMethods(HttpMethod.GET)
                                )
                                .circuitBreaker(config -> config
                                        .setName("socialMediaBreaker")
                                        .setFallbackUri("forward:/fallback/storage")))
                        .uri("lb://blob-service"))
                .route("post-service", r -> r
                        .path("/api/v1/posts/**")
                        .uri("lb://post-service"))
                .route("vote-service", r -> r
                        .path("/api/v1/votes/**")
                        .uri("lb://vote-service"))
                .route("service_registry", r -> r
                        .path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))
                .route("service_registry-static", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
