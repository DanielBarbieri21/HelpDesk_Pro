package com.helpdesk.helpdesk.state;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.StatusChamado;

public class EstadoAberto implements EstadoChamado {

    @Override
    public StatusChamado getStatus() {
        return StatusChamado.ABERTO;
    }

    @Override
    public void atender(Chamado chamado) {
        chamado.setEstado(new EstadoEmAtendimento());
    }

    @Override
    public void resolver(Chamado chamado) {
        chamado.setEstado(new EstadoResolvido());
    }
}
