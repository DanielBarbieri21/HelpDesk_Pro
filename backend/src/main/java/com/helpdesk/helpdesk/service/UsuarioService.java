package com.helpdesk.helpdesk.service;

import com.helpdesk.helpdesk.dto.UsuarioRequestDTO;
import com.helpdesk.helpdesk.dto.UsuarioResponseDTO;
import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.mapper.UsuarioMapper;
import com.helpdesk.helpdesk.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public Page<UsuarioResponseDTO> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toResponseDTO);
    }

    public UsuarioResponseDTO create(UsuarioRequestDTO requestDTO) {
        Usuario usuario = new Usuario();
        usuario.setName(requestDTO.getName());
        usuario.setEmail(requestDTO.getEmail());
        // A senha só é setada se fornecida, caso contrário geramos uma padrão ou retornamos erro
        usuario.setPassword(passwordEncoder.encode(requestDTO.getPassword() != null ? requestDTO.getPassword() : "123456"));
        usuario.setRole(requestDTO.getRole());
        usuario.setCreatedAt(LocalDateTime.now());
        
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO update(UUID id, UsuarioRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setName(requestDTO.getName());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setRole(requestDTO.getRole());
        
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }
        
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    public void delete(UUID id) {
        usuarioRepository.deleteById(id);
    }
}
