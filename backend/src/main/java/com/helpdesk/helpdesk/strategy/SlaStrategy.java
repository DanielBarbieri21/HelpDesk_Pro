package com.helpdesk.helpdesk.strategy;

import java.time.LocalDateTime;

// Strategy Pattern: Interface comum para cálculo de SLA
public interface SlaStrategy {
    LocalDateTime calcularVencimento(LocalDateTime dataCriacao);
}
