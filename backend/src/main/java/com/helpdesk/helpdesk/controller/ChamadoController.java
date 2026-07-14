package com.helpdesk.helpdesk.controller;

import com.helpdesk.helpdesk.dto.ChamadoRequestDTO;
import com.helpdesk.helpdesk.dto.ChamadoResponseDTO;
import com.helpdesk.helpdesk.dto.ComentarioRequestDTO;
import com.helpdesk.helpdesk.dto.ComentarioResponseDTO;
import com.helpdesk.helpdesk.service.ChamadoService;
import com.helpdesk.helpdesk.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService service;
    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criar(
            @RequestBody @Valid ChamadoRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.criarChamado(dto, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<Page<ChamadoResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // --- Transições de Estado ---

    @PutMapping("/{id}/atender")
    public ResponseEntity<ChamadoResponseDTO> atender(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.atenderChamado(id, userDetails.getUsername()));
    }

    @PutMapping("/{id}/aguardar-cliente")
    public ResponseEntity<ChamadoResponseDTO> aguardarCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(service.aguardarCliente(id));
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<ChamadoResponseDTO> resolver(@PathVariable UUID id) {
        return ResponseEntity.ok(service.resolverChamado(id));
    }

    @PutMapping("/{id}/fechar")
    public ResponseEntity<ChamadoResponseDTO> fechar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.fecharChamado(id));
    }

    @PutMapping("/{id}/reabrir")
    public ResponseEntity<ChamadoResponseDTO> reabrir(@PathVariable UUID id) {
        return ResponseEntity.ok(service.reabrirChamado(id));
    }

    @PutMapping("/{id}/atribuir-automaticamente")
    public ResponseEntity<ChamadoResponseDTO> atribuirAutomaticamente(@PathVariable UUID id) {
        return ResponseEntity.ok(service.atribuirAutomaticamente(id));
    }

    // --- Comentários ---
    
    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<ComentarioResponseDTO>> getComentarios(@PathVariable UUID id) {
        // We will need to update ComentarioService to use UUID for Chamado ID!
        return ResponseEntity.ok(comentarioService.getComentarios(id));
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioResponseDTO> addComentario(
            @PathVariable UUID id, 
            @Valid @RequestBody ComentarioRequestDTO dto, 
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(comentarioService.addComentario(id, userDetails.getUsername(), dto));
    }
}
