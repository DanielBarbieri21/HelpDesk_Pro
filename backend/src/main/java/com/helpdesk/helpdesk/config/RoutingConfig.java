package com.helpdesk.helpdesk.config;

import com.helpdesk.helpdesk.routing.AtribuicaoStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RoutingConfig {

    @Value("${helpdesk.routing.strategy:roundRobinStrategy}")
    private String strategyName;

    @Bean
    public AtribuicaoStrategy activeAtribuicaoStrategy(Map<String, AtribuicaoStrategy> strategies) {
        AtribuicaoStrategy strategy = strategies.get(strategyName);
        if (strategy == null) {
            throw new IllegalArgumentException("Estratégia de roteamento inválida: " + strategyName);
        }
        return strategy;
    }
}
