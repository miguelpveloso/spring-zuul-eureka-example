package io.lypsis.gateway;

import io.lypsis.gateway.filters.AuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@SpringBootApplication
@ComponentScan(basePackages = { "io.lypsis.gateway" })
public class ServiceGateway {

    public static void main(String[] args) {
        SpringApplication.run( ServiceGateway.class, args );
    }

    @Bean
    public AuthorizationFilter preFilter() {
        return new AuthorizationFilter();
    }

}

