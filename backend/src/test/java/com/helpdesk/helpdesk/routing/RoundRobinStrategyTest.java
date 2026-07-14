package com.helpdesk.helpdesk.routing;

import com.helpdesk.helpdesk.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundRobinStrategyTest {

    private RoundRobinStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new RoundRobinStrategy();
    }

    @Test
    void deveDistribuirEmOrdemCircular() {
        Usuario t1 = Usuario.builder().name("Tecnico 1").build();
        Usuario t2 = Usuario.builder().name("Tecnico 2").build();
        Usuario t3 = Usuario.builder().name("Tecnico 3").build();
        
        List<Usuario> tecnicos = List.of(t1, t2, t3);

        assertEquals(t1, strategy.determinarTecnico(tecnicos));
        assertEquals(t2, strategy.determinarTecnico(tecnicos));
        assertEquals(t3, strategy.determinarTecnico(tecnicos));
        // Volta para o primeiro
        assertEquals(t1, strategy.determinarTecnico(tecnicos));
    }
}
