package com.helpdesk.helpdesk.strategy;

import java.time.LocalDateTime;

// Strategy Pattern: Implementação para SLA Baixa (ex: 5 dias úteis)
// Por simplicidade, adicionando dias corridos, mas em produção consideraria calendário útil
public class SlaBaixaStrategy implements SlaStrategy {
    @Override
    public LocalDateTime calcularVencimento(LocalDateTime dataCriacao) {
        return dataCriacao.plusDays(5);
    }
}
