package io.lypsis.eureka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = { "io.lypsis.eureka" })
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
