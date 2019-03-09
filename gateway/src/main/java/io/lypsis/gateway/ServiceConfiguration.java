package io.lypsis.gateway;

import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.filters.ZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@ComponentScan(basePackages = { "io.lypsis.gateway" })
public class ServiceConfiguration {

    @Bean
    public WebSecurityConfigurerAdapter corsConfigurer() {
        return new WebSecurityConfigurerAdapter() {

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                        .authorizeRequests()
                        .anyRequest()
                        .permitAll()
                        .and()
                        .cors()
                        .and()
                        .csrf()
                        .disable();

            }
        };
    }


}
