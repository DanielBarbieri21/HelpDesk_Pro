package com.helpdesk.helpdesk.entity;

import com.helpdesk.helpdesk.state.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chamados")
@EntityListeners(AuditingEntityListener.class)
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status;

    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    // --- Integração com State Pattern ---
    
    @Transient
    private EstadoChamado estadoAtual;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void carregarEstado() {
        if (this.status != null) {
            this.estadoAtual = mapStatusToEstado(this.status);
        }
    }

    private EstadoChamado mapStatusToEstado(StatusChamado status) {
        return switch (status) {
            case ABERTO -> new EstadoAberto();
            case EM_ATENDIMENTO -> new EstadoEmAtendimento();
            case AGUARDANDO_CLIENTE -> new EstadoAguardandoCliente();
            case RESOLVIDO -> new EstadoResolvido();
            case FECHADO -> new EstadoFechado();
            case REABERTO -> new EstadoReaberto();
        };
    }

    public void setEstado(EstadoChamado novoEstado) {
        this.estadoAtual = novoEstado;
        this.status = novoEstado.getStatus();
    }

    public EstadoChamado getEstado() {
        if (estadoAtual == null && status != null) {
            carregarEstado();
        }
        return estadoAtual;
    }
    
    // Métodos delegados para a Máquina de Estados
    public void atender() { getEstado().atender(this); }
    public void aguardarCliente() { getEstado().aguardarCliente(this); }
    public void resolver() { getEstado().resolver(this); }
    public void fechar() { getEstado().fechar(this); }
    public void reabrir() { getEstado().reabrir(this); }

    // Método para calcular SLA via Strategy
    public void calcularSla() {
        if (this.prioridade != null && this.createdAt == null) {
            // Em nova criação, dataCriacao é agora (JPA Auditing ainda vai setar, mas precisamos para o SLA)
            this.dataVencimento = this.prioridade.getSlaStrategy().calcularVencimento(LocalDateTime.now());
        }
    }
}
