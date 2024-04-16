CREATE TABLE IF NOT EXISTS processamento (
     id SERIAL PRIMARY KEY,
     nome_arquivo VARCHAR(255),
     data TIMESTAMP WITHOUT TIME ZONE,
     total_registros INT,
     quantidade_processada INT,
     status VARCHAR(255)
);