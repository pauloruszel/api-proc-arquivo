CREATE TABLE IF NOT EXISTS segmento_veiculo (
    id_segmento_veiculo BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL
);
