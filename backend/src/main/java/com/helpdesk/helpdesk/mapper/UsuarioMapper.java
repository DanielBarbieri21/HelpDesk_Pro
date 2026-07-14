package com.helpdesk.helpdesk.mapper;

import com.helpdesk.helpdesk.dto.RegisterRequestDTO;
import com.helpdesk.helpdesk.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Usuario toEntity(RegisterRequestDTO dto);

    com.helpdesk.helpdesk.dto.UsuarioResponseDTO toResponseDTO(Usuario entity);
    
    com.helpdesk.helpdesk.dto.UsuarioProfileDTO toProfileDTO(Usuario entity);
}
