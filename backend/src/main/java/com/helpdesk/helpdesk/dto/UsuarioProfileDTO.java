package com.helpdesk.helpdesk.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UsuarioProfileDTO {
    private UUID id;
    private String name;
    private String email;
    private String role;
}
