CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    numeroConta VARCHAR(10) UNIQUE NOT NULL,
    saldo DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS transacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conta_origem VARCHAR(10) NOT NULL,
    conta_destino VARCHAR(10) NOT NULL,
    valor DOUBLE NOT NULL,
    data_transacao TIMESTAMP NOT NULL
);

INSERT INTO usuario (nome, idade, cpf, numeroConta, saldo) VALUES
('João Silva', 25, '12345678910', '12345', 1000.0),
('Maria Oliveira', 30, '98765432100', '67890', 1500.0),
('Carlos Souza', 35, '55555555555', '54321', 2000.0);

INSERT INTO transacao (conta_origem, conta_destino, valor, data_transacao) VALUES
('12345', '67890', 200.0, CURRENT_TIMESTAMP),
('67890', '54321', 150.0, CURRENT_TIMESTAMP),
('54321', '12345', 300.0, CURRENT_TIMESTAMP);

