CREATE TABLE chamados (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    prioridade VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_vencimento TIMESTAMP,
    solicitante_id UUID NOT NULL,
    tecnico_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    CONSTRAINT fk_solicitante FOREIGN KEY (solicitante_id) REFERENCES users(id),
    CONSTRAINT fk_tecnico FOREIGN KEY (tecnico_id) REFERENCES users(id)
);
