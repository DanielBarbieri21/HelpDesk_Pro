package com.helpdesk.helpdesk.service;

import com.helpdesk.helpdesk.dto.ChamadoRequestDTO;
import com.helpdesk.helpdesk.dto.ChamadoResponseDTO;
import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.factory.ChamadoFactory;
import com.helpdesk.helpdesk.mapper.ChamadoMapper;
import com.helpdesk.helpdesk.repository.ChamadoRepository;
import com.helpdesk.helpdesk.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository repository;
    private final ChamadoFactory factory;
    private final UsuarioRepository usuarioRepository;
    private final ChamadoMapper mapper;
    private final RoutingService routingService;

    @Transactional
    public ChamadoResponseDTO criarChamado(ChamadoRequestDTO dto, String userEmail) {
        Usuario solicitante = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Chamado chamado = factory.criarChamado(
                dto.getTitulo(),
                dto.getDescricao(),
                dto.getCategoria(),
                dto.getPrioridade()
        );
        
        chamado.setSolicitante(solicitante);
        Chamado salvo = repository.save(chamado);
        
        return mapper.toDto(salvo);
    }

    public Page<ChamadoResponseDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public ChamadoResponseDTO buscarPorId(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Chamado não encontrado"));
    }

    // --- Máquina de Estados ---
    @Transactional
    public ChamadoResponseDTO atenderChamado(UUID id, String tecnicoEmail) {
        Chamado chamado = getChamadoEntity(id);
        
        Usuario tecnico = usuarioRepository.findByEmail(tecnicoEmail)
                .orElseThrow(() -> new IllegalArgumentException("Técnico não encontrado"));

        // Se o chamado ainda não tem técnico, atribui a quem está atendendo
        if (chamado.getTecnico() == null) {
            chamado.setTecnico(tecnico);
        }

        // Tenta a transição. Lançará IllegalStateException se não puder
        chamado.atender();
        
        return mapper.toDto(repository.save(chamado));
    }

    @Transactional
    public ChamadoResponseDTO resolverChamado(UUID id) {
        Chamado chamado = getChamadoEntity(id);
        chamado.resolver();
        return mapper.toDto(repository.save(chamado));
    }

    @Transactional
    public ChamadoResponseDTO fecharChamado(UUID id) {
        Chamado chamado = getChamadoEntity(id);
        chamado.fechar();
        return mapper.toDto(repository.save(chamado));
    }

    @Transactional
    public ChamadoResponseDTO aguardarCliente(UUID id) {
        Chamado chamado = getChamadoEntity(id);
        chamado.aguardarCliente();
        return mapper.toDto(repository.save(chamado));
    }

    @Transactional
    public ChamadoResponseDTO reabrirChamado(UUID id) {
        Chamado chamado = getChamadoEntity(id);
        chamado.reabrir();
        return mapper.toDto(repository.save(chamado));
    }

    // --- Roteamento e Atribuição ---
    @Transactional
    public ChamadoResponseDTO atribuirAutomaticamente(UUID id) {
        Chamado chamado = getChamadoEntity(id);
        
        if (chamado.getTecnico() != null) {
            throw new IllegalStateException("Chamado já possui um técnico atribuído.");
        }

        Usuario tecnicoSelecionado = routingService.rotearChamado(chamado);
        chamado.setTecnico(tecnicoSelecionado);
        
        return mapper.toDto(repository.save(chamado));
    }

    private Chamado getChamadoEntity(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chamado não encontrado"));
    }
}
