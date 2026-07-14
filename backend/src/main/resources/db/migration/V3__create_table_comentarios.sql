CREATE TABLE comentarios (
    id UUID PRIMARY KEY,
    chamado_id UUID NOT NULL,
    autor_id UUID NOT NULL,
    conteudo TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_comentarios_chamado FOREIGN KEY (chamado_id) REFERENCES chamados(id) ON DELETE CASCADE,
    CONSTRAINT fk_comentarios_autor FOREIGN KEY (autor_id) REFERENCES users(id) ON DELETE CASCADE
);
