package com.helpdesk.helpdesk.repository;

import com.helpdesk.helpdesk.entity.Chamado;
import com.helpdesk.helpdesk.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, UUID> {
    List<Comentario> findByChamadoOrderByCreatedAtAsc(Chamado chamado);
}
