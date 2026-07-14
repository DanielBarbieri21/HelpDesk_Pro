package com.helpdesk.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ComentarioRequestDTO {
    @NotBlank(message = "O conteúdo não pode estar vazio")
    private String conteudo;
}
