package com.helpdesk.helpdesk.repository;

import com.helpdesk.helpdesk.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findByRole(com.helpdesk.helpdesk.entity.Role role);
}
