package com.helpdesk.helpdesk.routing;

import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.repository.ChamadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenorFilaStrategyTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    @InjectMocks
    private MenorFilaStrategy strategy;

    @Test
    void deveEscolherTecnicoComMenorFila() {
        Usuario t1 = Usuario.builder().id(java.util.UUID.randomUUID()).name("Tecnico 1").build();
        Usuario t2 = Usuario.builder().id(java.util.UUID.randomUUID()).name("Tecnico 2").build();
        
        List<Usuario> tecnicos = List.of(t1, t2);

        // t1 tem 5 chamados
        when(chamadoRepository.countByTecnicoAndStatusIn(eq(t1), any())).thenReturn(5L);
        // t2 tem 2 chamados
        when(chamadoRepository.countByTecnicoAndStatusIn(eq(t2), any())).thenReturn(2L);

        Usuario escolhido = strategy.determinarTecnico(tecnicos);
        
        assertEquals(t2, escolhido);
    }
}
