package com.helpdesk.helpdesk.mapper;

import com.helpdesk.helpdesk.dto.ChamadoResponseDTO;
import com.helpdesk.helpdesk.entity.Chamado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChamadoMapper {

    @Mapping(target = "solicitanteId", source = "solicitante.id")
    @Mapping(target = "solicitanteNome", source = "solicitante.name")
    @Mapping(target = "tecnicoId", source = "tecnico.id")
    @Mapping(target = "tecnicoNome", source = "tecnico.name")
    ChamadoResponseDTO toDto(Chamado entity);
}
