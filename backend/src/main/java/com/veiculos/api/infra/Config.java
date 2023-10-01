package com.veiculos.api.infra;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Class responsible configuration of spring beans for the app.
 * @author Péricles Tavares
 */
@Configuration
@PropertySource("classpath:ValidationMessages.properties")
public class Config {

    /**
     * Create and config the ModelMapper for the DTO entity conversion.
     * @return ModelMapper bean
     */
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
