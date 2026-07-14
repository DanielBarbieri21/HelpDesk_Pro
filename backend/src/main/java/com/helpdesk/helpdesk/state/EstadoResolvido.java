package com.helpdesk.helpdesk.state;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.StatusChamado;

public class EstadoResolvido implements EstadoChamado {

    @Override
    public StatusChamado getStatus() {
        return StatusChamado.RESOLVIDO;
    }

    @Override
    public void fechar(Chamado chamado) {
        chamado.setEstado(new EstadoFechado());
    }

    @Override
    public void reabrir(Chamado chamado) {
        chamado.setEstado(new EstadoReaberto());
    }
}
