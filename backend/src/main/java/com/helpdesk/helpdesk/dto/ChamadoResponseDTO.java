package com.helpdesk.helpdesk.dto;

import com.helpdesk.helpdesk.entity.Categoria;
import com.helpdesk.helpdesk.entity.Prioridade;
import com.helpdesk.helpdesk.entity.StatusChamado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoResponseDTO {
    private UUID id;
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private Prioridade prioridade;
    private StatusChamado status;
    private LocalDateTime dataVencimento;
    private UUID solicitanteId;
    private String solicitanteNome;
    private UUID tecnicoId;
    private String tecnicoNome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
