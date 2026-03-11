package com.andy.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    /**
     * 自定义动态路由规则
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 系统服务路由
                .route("system-service", r -> r.path("/system/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://system-service"))
                // 认证服务路由
                .route("passport-service", r -> r.path("/auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://passport-service"))
                .build();
    }
}