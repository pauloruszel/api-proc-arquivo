CREATE TABLE IF NOT EXISTS modelo_veiculo (
    id_modelo_veiculo BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL,
    versao VARCHAR(255),
    motor VARCHAR(255),
    transmissao VARCHAR(255),
    ano_modelo VARCHAR(4),
    ano_fabricacao VARCHAR(4)
);
