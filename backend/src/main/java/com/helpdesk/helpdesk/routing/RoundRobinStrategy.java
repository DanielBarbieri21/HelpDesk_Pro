package com.helpdesk.helpdesk.routing;

import com.helpdesk.helpdesk.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Strategy: Distribui os chamados de forma circular entre os técnicos disponíveis.
 * Utiliza AtomicInteger para garantir thread-safety num ambiente multithreaded web.
 */
@Component("roundRobinStrategy")
public class RoundRobinStrategy implements AtribuicaoStrategy {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public Usuario determinarTecnico(List<Usuario> tecnicosDisponiveis) {
        if (tecnicosDisponiveis == null || tecnicosDisponiveis.isEmpty()) {
            throw new IllegalStateException("Nenhum técnico disponível para atribuição.");
        }

        int size = tecnicosDisponiveis.size();
        // Garante que o índice avance e resete utilizando módulo de forma atômica
        int currentIndex = index.getAndUpdate(i -> (i + 1) % size);

        // Fail-safe caso a lista diminua de tamanho subitamente
        if (currentIndex >= size) {
            currentIndex = 0;
            index.set(1);
        }

        return tecnicosDisponiveis.get(currentIndex);
    }
}
