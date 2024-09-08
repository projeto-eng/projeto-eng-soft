package br.usp.ime.projetoengsoft.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class Api {

    @Configuration
    public static class WebConfiguration implements WebMvcConfigurer {
        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
            configurer.addPathPrefix(
                    "/api",
                    HandlerTypePredicate.forAnnotation(RestController.class)
            );
        }
    }
}
