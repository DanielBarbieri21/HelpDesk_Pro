package com.helpdesk.helpdesk.config;

import com.helpdesk.helpdesk.entity.Role;
import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setName("Administrador");
            admin.setEmail("admin@helpdesk.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setCreatedBy("system-seeder");
            
            usuarioRepository.save(admin);
            System.out.println("✅ Usuário ADMIN criado com sucesso: admin@helpdesk.com / admin123");
        }
    }
}
