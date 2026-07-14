package com.helpdesk.helpdesk.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DashboardDTO {
    private long totalChamados;
    private Map<String, Long> chamadosPorStatus;
    private Map<String, Long> chamadosPorPrioridade;
}
