package com.momentarek.billingsoftware; // TODO: change this to match your actual base package (same as your main Application class)

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
            "/api/v1.0",
            HandlerTypePredicate.forAnnotation(org.springframework.web.bind.annotation.RestController.class)
                .and(handlerType -> !handlerType.equals(HealthController.class))
        );
    }
}
