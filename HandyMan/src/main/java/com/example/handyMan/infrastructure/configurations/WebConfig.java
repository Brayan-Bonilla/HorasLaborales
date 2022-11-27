package com.example.handyMan.infrastructure.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Establece configuraciones para la interfaz web
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry)
    {
        viewControllerRegistry.addViewController("/").setViewName("index");
    }

}
