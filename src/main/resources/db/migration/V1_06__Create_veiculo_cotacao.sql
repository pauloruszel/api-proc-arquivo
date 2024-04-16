CREATE TABLE IF NOT EXISTS veiculo_cotacao (
    id_veiculo_cotacao BIGSERIAL PRIMARY KEY,
    id_veiculo BIGINT REFERENCES veiculo(id_veiculo),
    id_cotacao BIGINT REFERENCES cotacao(id_cotacao),
    id_tipo_pessoa BIGINT REFERENCES tipo_pessoa(id_tipo_pessoa),
    ativo BOOLEAN NOT NULL
);
