CREATE TABLE IF NOT EXISTS Processamento (
    id SERIAL PRIMARY KEY,
    nome_arquivo VARCHAR(255),
    data TIMESTAMP WITHOUT TIME ZONE,
    total_registros INT,
    quantidade_processada INT,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ProcessamentoErro (
    id SERIAL PRIMARY KEY,
    id_processamento BIGINT,
    status VARCHAR(50),
    data TIMESTAMP WITHOUT TIME ZONE,
    payload TEXT,
    FOREIGN KEY (id_processamento) REFERENCES Processamento(id)
);

CREATE TABLE IF NOT EXISTS Planos (
    id SERIAL PRIMARY KEY,
    nome_plano VARCHAR(255),
    data TIMESTAMP WITHOUT TIME ZONE
);
