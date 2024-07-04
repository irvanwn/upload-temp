package com.ecommerce.desktop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DesktopApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesktopApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("product_route", r -> r.path("/api/products/**")
						.uri("http://localhost:8081"))
				.route("store_route", r -> r.path("/api/stores/**")
						.uri("http://localhost:8082"))
				.route("cart_route", r -> r.path("/api/cart/**")
						.uri("http://localhost:8083"))
				.route("shipping_route", r -> r.path("/api/shipping/**")
						.uri("http://localhost:8084"))
				.route("transaction_route", r -> r.path("/api/transaction/**")
						.uri("http://localhost:8085"))
				.build();
	}
}
