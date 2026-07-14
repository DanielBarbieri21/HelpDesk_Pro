package com.helpdesk.helpdesk.strategy;

import java.time.LocalDateTime;

public class SlaAltaStrategy implements SlaStrategy {
    @Override
    public LocalDateTime calcularVencimento(LocalDateTime dataCriacao) {
        return dataCriacao.plusDays(1);
    }
}
