package com.helpdesk.helpdesk.mapper;

import com.helpdesk.helpdesk.dto.ComentarioResponseDTO;
import com.helpdesk.helpdesk.entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {
    @Mapping(target = "chamadoId", source = "chamado.id")
    @Mapping(target = "autorId", source = "autor.id")
    @Mapping(target = "autorNome", source = "autor.name")
    @Mapping(target = "autorRole", source = "autor.role")
    ComentarioResponseDTO toResponseDTO(Comentario entity);
}
