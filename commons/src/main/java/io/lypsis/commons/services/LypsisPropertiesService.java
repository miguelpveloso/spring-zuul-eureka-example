package io.lypsis.commons.services;

import io.lypsis.commons.properties.LypsisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableConfigurationProperties(LypsisProperties.class)
public class LypsisPropertiesService {

    @Autowired
    LypsisProperties lypsisProperties;

    public Map<String, String> api() {
        return this.lypsisProperties.getApi();
    }

}
