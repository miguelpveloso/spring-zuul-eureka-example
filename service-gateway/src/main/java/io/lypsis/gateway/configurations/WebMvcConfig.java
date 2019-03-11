package io.lypsis.gateway.configurations;

import io.lypsis.commons.services.LypsisPropertiesService;
import io.lypsis.gateway.interceptors.RequestProcessingTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.lypsis.commons.properties.LypsisProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@ComponentScan(basePackages = { "io.lypsis" })
@EnableConfigurationProperties(LypsisProperties.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private boolean developmentEnv;

    @Autowired
    private RequestProcessingTimeInterceptor rpti;

    @Autowired
    private LypsisPropertiesService lypsisPropertiesService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rpti);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping(lypsisPropertiesService.api().get("api"))
        //.allowedOrigins("*")
        .allowedMethods(
                HttpMethod.HEAD.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()
        )
        .exposedHeaders(HttpHeaders.AUTHORIZATION)
        .allowCredentials(false);
        //.maxAge(this.maxAgeSeconds);
    }

    /**
     * Makes sure the /docs/ and /docs/javadoc/ load the contained index.html
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers( registry );
        registry.addViewController( "/" ).setViewName( "forward:/index.html" );
        registry.addViewController( "/docs/" ).setViewName( "forward:/docs/api-guide.html" );
        registry.addViewController( "/docs/javadoc/" ).setViewName( "forward:/docs/javadoc/index.html" );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if (developmentEnv) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
}
