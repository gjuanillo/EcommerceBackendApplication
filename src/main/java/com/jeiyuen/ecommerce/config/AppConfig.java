package com.jeiyuen.ecommerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // How model mapper will be called
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
