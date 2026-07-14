package com.helpdesk.helpdesk.dto;

import com.helpdesk.helpdesk.entity.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UsuarioResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private Role role;
}
