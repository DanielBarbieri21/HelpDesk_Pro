package com.helpdesk.helpdesk.strategy;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlaStrategyTest {

    @Test
    void testSlaCritica() {
        SlaStrategy strategy = new SlaCriticaStrategy();
        LocalDateTime agora = LocalDateTime.of(2023, 10, 10, 10, 0);
        
        LocalDateTime vencimento = strategy.calcularVencimento(agora);
        
        assertEquals(LocalDateTime.of(2023, 10, 10, 14, 0), vencimento);
    }

    @Test
    void testSlaMedia() {
        SlaStrategy strategy = new SlaMediaStrategy();
        LocalDateTime agora = LocalDateTime.of(2023, 10, 10, 10, 0);
        
        LocalDateTime vencimento = strategy.calcularVencimento(agora);
        
        assertEquals(LocalDateTime.of(2023, 10, 13, 10, 0), vencimento);
    }
}
