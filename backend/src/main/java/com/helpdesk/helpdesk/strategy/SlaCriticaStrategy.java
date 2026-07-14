package com.helpdesk.helpdesk.strategy;

import java.time.LocalDateTime;

public class SlaCriticaStrategy implements SlaStrategy {
    @Override
    public LocalDateTime calcularVencimento(LocalDateTime dataCriacao) {
        return dataCriacao.plusHours(4);
    }
}
