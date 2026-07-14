package com.helpdesk.helpdesk.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ComentarioResponseDTO {
    private UUID id;
    private UUID chamadoId;
    private UUID autorId;
    private String autorNome;
    private String autorRole;
    private String conteudo;
    private LocalDateTime createdAt;
}
