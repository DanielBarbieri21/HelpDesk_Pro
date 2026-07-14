package com.helpdesk.helpdesk.state;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.StatusChamado;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTransitionTest {

    @Test
    void shouldTransitionFromAbertoToEmAtendimento() {
        Chamado chamado = new Chamado();
        chamado.setEstado(new EstadoAberto());
        
        chamado.atender();
        
        assertEquals(StatusChamado.EM_ATENDIMENTO, chamado.getStatus());
        assertTrue(chamado.getEstadoAtual() instanceof EstadoEmAtendimento);
    }

    @Test
    void shouldThrowExceptionWhenAbertoToFechado() {
        Chamado chamado = new Chamado();
        chamado.setEstado(new EstadoAberto());
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, chamado::fechar);
        assertTrue(exception.getMessage().contains("não é possível fechar a partir do estado ABERTO"));
    }

    @Test
    void shouldTransitionFromResolvidoToFechado() {
        Chamado chamado = new Chamado();
        chamado.setEstado(new EstadoResolvido());
        
        chamado.fechar();
        
        assertEquals(StatusChamado.FECHADO, chamado.getStatus());
    }

    @Test
    void shouldTransitionFromFechadoToReaberto() {
        Chamado chamado = new Chamado();
        chamado.setEstado(new EstadoFechado());
        
        chamado.reabrir();
        
        assertEquals(StatusChamado.REABERTO, chamado.getStatus());
    }
}
