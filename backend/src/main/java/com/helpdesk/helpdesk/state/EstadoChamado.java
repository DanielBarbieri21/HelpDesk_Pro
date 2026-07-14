package com.helpdesk.helpdesk.state;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.StatusChamado;

// State Pattern: Define os comportamentos permitidos para cada estado
public interface EstadoChamado {
    
    StatusChamado getStatus();

    default void atender(Chamado chamado) {
        throw new IllegalStateException("Transição não permitida: não é possível atender a partir do estado " + getStatus());
    }

    default void aguardarCliente(Chamado chamado) {
        throw new IllegalStateException("Transição não permitida: não é possível aguardar cliente a partir do estado " + getStatus());
    }

    default void resolver(Chamado chamado) {
        throw new IllegalStateException("Transição não permitida: não é possível resolver a partir do estado " + getStatus());
    }

    default void fechar(Chamado chamado) {
        throw new IllegalStateException("Transição não permitida: não é possível fechar a partir do estado " + getStatus());
    }

    default void reabrir(Chamado chamado) {
        throw new IllegalStateException("Transição não permitida: não é possível reabrir a partir do estado " + getStatus());
    }
}
