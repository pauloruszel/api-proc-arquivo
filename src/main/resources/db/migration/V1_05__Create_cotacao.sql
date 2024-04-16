CREATE TABLE IF NOT EXISTS cotacao (
    id_cotacao BIGSERIAL PRIMARY KEY,
    codigo_cotacao VARCHAR(255),
    fsc VARCHAR(255),
    ocn VARCHAR(255),
    pack VARCHAR(255),
    prazo INT,
    km BIGINT,
    km_mensal BIGINT,
    mensalidade NUMERIC(19,2),
    km_superior NUMERIC(10,2),
    pneus INT,
    cooparticipacao_acidente NUMERIC(19,2),
    cidade_circulacao VARCHAR(255),
    ativo BOOLEAN NOT NULL
);
