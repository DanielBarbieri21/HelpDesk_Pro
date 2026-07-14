package com.helpdesk.helpdesk.repository;

import com.helpdesk.helpdesk.entity.Categoria;
import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.Prioridade;
import com.helpdesk.helpdesk.entity.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, UUID> {
    
    Page<Chamado> findByStatus(StatusChamado status, Pageable pageable);
    
    Page<Chamado> findByPrioridade(Prioridade prioridade, Pageable pageable);
    
    Page<Chamado> findByCategoria(Categoria categoria, Pageable pageable);
    
    Page<Chamado> findByTecnicoId(UUID tecnicoId, Pageable pageable);
    
    Page<Chamado> findBySolicitanteId(UUID solicitanteId, Pageable pageable);

    long countByTecnicoAndStatusIn(com.helpdesk.helpdesk.entity.Usuario tecnico, java.util.Collection<StatusChamado> statuses);
}
