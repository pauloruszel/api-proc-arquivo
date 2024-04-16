CREATE TABLE IF NOT EXISTS processamento (
    id SERIAL PRIMARY KEY,
    nome_arquivo VARCHAR(255),
    data TIMESTAMP WITHOUT TIME ZONE,
    total_registros INT,
    quantidade_processada INT,
    status VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS processamento_erro (
    id SERIAL PRIMARY KEY,
    id_processamento INTEGER NOT NULL,
    status VARCHAR(255),
    data TIMESTAMP,
    payload VARCHAR(255),
    FOREIGN KEY (id_processamento) REFERENCES processamento(id)
);

CREATE TABLE IF NOT EXISTS planos (
    id SERIAL PRIMARY KEY,
    nome_plano VARCHAR(255),
    data TIMESTAMP WITHOUT TIME ZONE
);
