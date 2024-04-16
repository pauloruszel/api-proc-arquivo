CREATE TABLE IF NOT EXISTS processamento_erro (
      id SERIAL PRIMARY KEY,
      id_processamento INTEGER NOT NULL,
      status VARCHAR(255),
      data TIMESTAMP,
      payload VARCHAR(255),
      FOREIGN KEY (id_processamento) REFERENCES processamento(id)
);