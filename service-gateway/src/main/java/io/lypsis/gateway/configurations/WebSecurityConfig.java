package io.lypsis.gateway.configurations;

import io.lypsis.commons.properties.LypsisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private boolean securityRequireSsl = false;

    @Autowired
    private boolean developmentEnv;

    @Autowired
    private LypsisProperties lypsisProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity
                .cors().and()
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Any other endpoints should be secured by jwtAuthenticationFilter
                .authorizeRequests();

        expressionInterceptUrlRegistry
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        // Add security constraints based on the customerConfig.json file
        // securityAuthMatchers.addSecurityAntMatchers(expressionInterceptUrlRegistry);

        // Any endpoints should be secured by jwtAuthenticationFilter
        // expressionInterceptUrlRegistry.anyRequest().authenticated();


        if (securityRequireSsl) {
            // Force the use of HTTPS for any request
            httpSecurity.requiresChannel()
                    .anyRequest()
                    .requiresSecure();
        }

        // Add our custom JWT security filter
        // httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // Add after authentication filter for customer context
        // httpSecurity.addFilterAfter(customerContextFilter(), JwtAuthenticationFilter.class);
        // Add before JWT security filter to use X-Customer header
        // httpSecurity.addFilterBefore(customAuthenticationFilter(), JwtAuthenticationFilter.class);

        // By default X-Frame-Options is set to denied, to prevent clickjacking attacks.
        // To override this, you can add the following into your spring security config
        // httpSecurity.headers().frameOptions().sameOrigin();

        //Make sure the security context is available from spawned threads
        // SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        List<String> patterns = new ArrayList<>(
                Arrays.asList("/",
                "/index.html",
                "/favicon.ico",
                "/docs/**",
                "/**/.ico",
                "/**/*.txt",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.eot",
                "/**/*.ttf",
                "/**/*.woff",
                "/**/*.woff2",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.css",
                "/**/*.js",
                "/**/*.map"));

        if (developmentEnv) {
            log.info("Enabling Swagger");
            patterns.addAll(
                    Arrays.asList("/**/v2/api-docs/**",
                    "/**/swagger-resources/**",
                    "/**/swagger-ui.html",
                    "/**/webjars/**")
            );
        }

        // JwtAuthenticationFilter will ignore the below paths
        String[] antPatterns = patterns.toArray(new String[0]);
        web.debug(false)
                .ignoring().antMatchers(antPatterns);
    }
}
