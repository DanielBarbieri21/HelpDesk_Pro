package com.helpdesk.helpdesk.state;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.StatusChamado;

public class EstadoFechado implements EstadoChamado {

    @Override
    public StatusChamado getStatus() {
        return StatusChamado.FECHADO;
    }
    
    // Fechado é terminal. Para reabrir, talvez o ADMIN possa, 
    // mas pela regra geralmente resolvido -> reabrir. 
    // Vamos permitir reabrir do FECHADO também para flexibilidade.
    @Override
    public void reabrir(Chamado chamado) {
        chamado.setEstado(new EstadoReaberto());
    }
}
