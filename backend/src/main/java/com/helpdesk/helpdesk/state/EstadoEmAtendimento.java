package com.helpdesk.helpdesk.state;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.StatusChamado;

public class EstadoEmAtendimento implements EstadoChamado {

    @Override
    public StatusChamado getStatus() {
        return StatusChamado.EM_ATENDIMENTO;
    }

    @Override
    public void aguardarCliente(Chamado chamado) {
        chamado.setEstado(new EstadoAguardandoCliente());
    }

    @Override
    public void resolver(Chamado chamado) {
        chamado.setEstado(new EstadoResolvido());
    }
}
