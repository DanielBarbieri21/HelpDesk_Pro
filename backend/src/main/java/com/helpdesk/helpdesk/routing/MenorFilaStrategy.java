package com.helpdesk.helpdesk.routing;

import com.helpdesk.helpdesk.entity.StatusChamado;
import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Strategy: Distribui o chamado para o técnico com a menor quantidade
 * de chamados ativos (Aberto ou Em Atendimento). Ideal para balanceamento
 * dinâmico de carga (Least Connections).
 */
@Component("menorFilaStrategy")
@RequiredArgsConstructor
public class MenorFilaStrategy implements AtribuicaoStrategy {

    private final ChamadoRepository chamadoRepository;

    @Override
    public Usuario determinarTecnico(List<Usuario> tecnicosDisponiveis) {
        if (tecnicosDisponiveis == null || tecnicosDisponiveis.isEmpty()) {
            throw new IllegalStateException("Nenhum técnico disponível para atribuição.");
        }

        Usuario tecnicoComMenorFila = null;
        long menorQuantidade = Long.MAX_VALUE;

        List<StatusChamado> statusAtivos = Arrays.asList(StatusChamado.ABERTO, StatusChamado.EM_ATENDIMENTO);

        for (Usuario tecnico : tecnicosDisponiveis) {
            long chamadosAtivos = chamadoRepository.countByTecnicoAndStatusIn(tecnico, statusAtivos);

            if (chamadosAtivos < menorQuantidade) {
                menorQuantidade = chamadosAtivos;
                tecnicoComMenorFila = tecnico;
            }
        }

        return tecnicoComMenorFila;
    }
}
