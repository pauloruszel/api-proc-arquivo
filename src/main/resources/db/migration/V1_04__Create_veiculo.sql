CREATE TABLE IF NOT EXISTS veiculo (
    id_veiculo BIGSERIAL PRIMARY KEY,
    marca VARCHAR(255) NOT NULL,
    id_segmento_veiculo BIGINT REFERENCES segmento_veiculo(id_segmento_veiculo),
    id_modelo_veiculo BIGINT REFERENCES modelo_veiculo(id_modelo_veiculo)
);
