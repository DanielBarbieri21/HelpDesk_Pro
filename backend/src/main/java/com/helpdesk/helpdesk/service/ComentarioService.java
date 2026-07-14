package com.helpdesk.helpdesk.service;

import com.helpdesk.helpdesk.dto.ComentarioRequestDTO;
import com.helpdesk.helpdesk.dto.ComentarioResponseDTO;
import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.Comentario;
import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.mapper.ComentarioMapper;
import com.helpdesk.helpdesk.repository.ChamadoRepository;
import com.helpdesk.helpdesk.repository.ComentarioRepository;
import com.helpdesk.helpdesk.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioMapper comentarioMapper;

    public ComentarioResponseDTO addComentario(UUID chamadoId, String email, ComentarioRequestDTO dto) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
            .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        
        Usuario autor = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Comentario comentario = new Comentario();
        comentario.setChamado(chamado);
        comentario.setAutor(autor);
        comentario.setConteudo(dto.getConteudo());
        comentario.setCreatedAt(LocalDateTime.now());

        comentario = comentarioRepository.save(comentario);
        return comentarioMapper.toResponseDTO(comentario);
    }

    public List<ComentarioResponseDTO> getComentarios(UUID chamadoId) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
            .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
            
        return comentarioRepository.findByChamadoOrderByCreatedAtAsc(chamado)
            .stream()
            .map(comentarioMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
}
