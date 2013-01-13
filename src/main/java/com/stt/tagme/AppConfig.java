package com.stt.tagme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@Configuration
@ComponentScan(basePackages = "com.stt.tagme")
public class AppConfig {

    @Bean
    public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter()
    {
        final AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter = new AnnotationMethodHandlerAdapter();
        final MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();

        HttpMessageConverter<?>[] httpMessageConverter = { mappingJacksonHttpMessageConverter };

        String[] supportedHttpMethods = { "POST", "GET", "HEAD" };

        annotationMethodHandlerAdapter.setMessageConverters(httpMessageConverter);
        annotationMethodHandlerAdapter.setSupportedMethods(supportedHttpMethods);

        return annotationMethodHandlerAdapter;
    }
}