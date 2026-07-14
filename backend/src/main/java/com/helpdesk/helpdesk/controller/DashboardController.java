package com.helpdesk.helpdesk.controller;

import com.helpdesk.helpdesk.dto.DashboardDTO;
import com.helpdesk.helpdesk.entity.Prioridade;
import com.helpdesk.helpdesk.entity.StatusChamado;
import com.helpdesk.helpdesk.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ChamadoRepository chamadoRepository;

    @GetMapping("/estatisticas")
    public ResponseEntity<DashboardDTO> getEstatisticas() {
        long total = chamadoRepository.count();

        Map<String, Long> status = Map.of(
            "Aberto", chamadoRepository.countByStatus(StatusChamado.ABERTO),
            "Em Atendimento", chamadoRepository.countByStatus(StatusChamado.EM_ATENDIMENTO),
            "Aguardando Cliente", chamadoRepository.countByStatus(StatusChamado.AGUARDANDO_CLIENTE),
            "Resolvido", chamadoRepository.countByStatus(StatusChamado.RESOLVIDO),
            "Fechado", chamadoRepository.countByStatus(StatusChamado.FECHADO)
        );

        Map<String, Long> prioridade = Map.of(
            "Baixa", chamadoRepository.countByPrioridade(Prioridade.BAIXA),
            "Média", chamadoRepository.countByPrioridade(Prioridade.MEDIA),
            "Alta", chamadoRepository.countByPrioridade(Prioridade.ALTA),
            "Crítica", chamadoRepository.countByPrioridade(Prioridade.CRITICA)
        );

        DashboardDTO dashboard = DashboardDTO.builder()
                .totalChamados(total)
                .chamadosPorStatus(status)
                .chamadosPorPrioridade(prioridade)
                .build();

        return ResponseEntity.ok(dashboard);
    }
}
