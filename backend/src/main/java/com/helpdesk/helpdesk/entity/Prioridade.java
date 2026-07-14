package com.helpdesk.helpdesk.entity;

import com.helpdesk.helpdesk.strategy.*;

public enum Prioridade {
    BAIXA(new SlaBaixaStrategy()),
    MEDIA(new SlaMediaStrategy()),
    ALTA(new SlaAltaStrategy()),
    CRITICA(new SlaCriticaStrategy());

    private final SlaStrategy slaStrategy;

    Prioridade(SlaStrategy slaStrategy) {
        this.slaStrategy = slaStrategy;
    }

    public SlaStrategy getSlaStrategy() {
        return slaStrategy;
    }
}
