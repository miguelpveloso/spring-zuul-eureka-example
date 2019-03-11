package io.lypsis.gateway.configurations;

import io.lypsis.commons.filters.HttpsEnforcerFilter;
import io.lypsis.commons.properties.LypsisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.time.Clock;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Configuration
@EnableConfigurationProperties({LypsisProperties.class})
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    private final Environment environment;

    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Clock defaultClock() {
        return Clock.systemDefaultZone();
    }

    private boolean securityRequireSsl = false;

    @Value("classpath:customerConfig.json")
    private Resource customerConfigResource;

    // Add before any security filter to evaluate X-Forwarded-Proto / https header
    @Bean
    public FilterRegistrationBean httpsEnforcerFilter() {
        LOGGER.debug("Add filter for https enforcer: {}", securityRequireSsl);
        FilterRegistrationBean result = new FilterRegistrationBean();
        result.setFilter(new HttpsEnforcerFilter(securityRequireSsl));
        result.addUrlPatterns("/*");
        result.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return result;
    }

    @Bean(name = "developmentEnv")
    public boolean developmentEnv() {
        Predicate<String> profileMatch = p -> "dev".equalsIgnoreCase(p) || "local".equalsIgnoreCase(p);
        return  (Stream.of(environment.getActiveProfiles()).anyMatch(profileMatch));
    }

}
