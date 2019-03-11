package io.lypsis.commons.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.ComponentScan;

import io.lypsis.commons.properties.utils.JwtProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Configuration
@ConfigurationProperties(prefix = "lypsis")
@ComponentScan(basePackages = { "io.lypsis.commons" })
@PropertySource("classpath:lypsis.yaml")
public class LypsisProperties {

    @NestedConfigurationProperty
    private JwtProperties jwt;

    @NestedConfigurationProperty
    private Map<String, String> api;

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public Map<String, String> getApi() {
        return api;
    }

    public void setApi(Map<String, String> api) {
        this.api = api;
    }

}
