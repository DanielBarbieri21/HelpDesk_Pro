package com.helpdesk.helpdesk.service;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.Role;
import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.repository.UsuarioRepository;
import com.helpdesk.helpdesk.routing.AtribuicaoStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutingService {

    private final AtribuicaoStrategy activeStrategy;
    private final UsuarioRepository usuarioRepository;

    /**
     * Orquestra a atribuição do chamado utilizando a estratégia ativa configurada.
     * 
     * @param chamado Chamado que receberá a atribuição
     * @return O usuário técnico escolhido
     */
    public Usuario rotearChamado(Chamado chamado) {
        // Busca todos os técnicos elegíveis (ex: Role = TECNICO)
        // No contexto do nosso projeto, assumimos Role.TECNICO ou ADMIN para fins de teste.
        List<Usuario> tecnicos = usuarioRepository.findByRole(Role.TECNICO);
        
        if (tecnicos.isEmpty()) {
            // Fallback caso não haja TECNICO, procura ADMIN
            tecnicos = usuarioRepository.findByRole(Role.ADMIN);
            if (tecnicos.isEmpty()) {
                throw new IllegalStateException("Não há técnicos disponíveis para roteamento do chamado.");
            }
        }

        return activeStrategy.determinarTecnico(tecnicos);
    }
}
