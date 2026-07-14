package com.helpdesk.helpdesk.controller;

import com.helpdesk.helpdesk.dto.ChamadoRequestDTO;
import com.helpdesk.helpdesk.dto.ChamadoResponseDTO;
import com.helpdesk.helpdesk.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService service;

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
}
