CREATE TABLE IF NOT EXISTS tipo_pessoa (
    id_tipo_pessoa BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL
);
