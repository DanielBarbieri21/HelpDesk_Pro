package com.helpdesk.helpdesk.factory;

import com.helpdesk.helpdesk.entity.Categoria;
import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.Prioridade;
import com.helpdesk.helpdesk.state.EstadoAberto;
import org.springframework.stereotype.Component;

// Factory Pattern: Centraliza a criação de instâncias de Chamado,
// permitindo configurações específicas baseadas na categoria.
@Component
public class ChamadoFactory {

    public Chamado criarChamado(String titulo, String descricao, Categoria categoria, Prioridade prioridade) {
        Chamado chamado = Chamado.builder()
                .titulo(titulo)
                .descricao(descricao)
                .categoria(categoria)
                .prioridade(prioridade)
                .build();
        
        // Define estado inicial
        chamado.setEstado(new EstadoAberto());
        
        // Regras específicas por categoria podem ser injetadas aqui. 
        // Exemplo: se for REDE e prioridade não foi informada, força ALTA.
        // Se for ACESSO, adiciona um template à descrição.
        if (categoria == Categoria.ACESSO) {
            chamado.setDescricao("[Template de Acesso]\n" + chamado.getDescricao());
        }

        // Calcula a data limite do SLA usando a Strategy injetada no Enum Prioridade
        chamado.calcularSla();

        return chamado;
    }
}
