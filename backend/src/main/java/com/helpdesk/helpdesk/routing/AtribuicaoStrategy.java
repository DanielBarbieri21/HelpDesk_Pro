package com.helpdesk.helpdesk.routing;

import com.helpdesk.helpdesk.entity.Usuario;

import java.util.List;

public interface AtribuicaoStrategy {
    
    /**
     * Determina qual técnico deve receber o chamado com base em um algoritmo específico.
     * 
     * @param tecnicosDisponiveis Lista de técnicos elegíveis para receber o chamado
     * @return Usuario (o técnico selecionado)
     */
    Usuario determinarTecnico(List<Usuario> tecnicosDisponiveis);
}
