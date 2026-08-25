CREATE DATABASE IF NOT EXISTS grupo01_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE grupo01_db;

CREATE TABLE IF NOT EXISTS autor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    nacionalidade VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS editora (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cidade VARCHAR(80)
);

CREATE TABLE IF NOT EXISTS aluno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    matricula VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(150),
    telefone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS livro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(160) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    categoria VARCHAR(80),
    ano_publicacao INT,
    quantidade_total INT NOT NULL DEFAULT 1,
    quantidade_disponivel INT NOT NULL DEFAULT 1,
    autor_id BIGINT NOT NULL,
    editora_id BIGINT NOT NULL,
    CONSTRAINT fk_livro_autor FOREIGN KEY (autor_id) REFERENCES autor(id),
    CONSTRAINT fk_livro_editora FOREIGN KEY (editora_id) REFERENCES editora(id)
);

CREATE TABLE IF NOT EXISTS emprestimo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    livro_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_prevista_devolucao DATE NOT NULL,
    data_devolucao DATE,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_emprestimo_livro FOREIGN KEY (livro_id) REFERENCES livro(id),
    CONSTRAINT fk_emprestimo_aluno FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);

CREATE TABLE IF NOT EXISTS multa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    emprestimo_id BIGINT NOT NULL UNIQUE,
    dias_atraso INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_geracao DATE NOT NULL,
    data_pagamento DATE,
    CONSTRAINT fk_multa_emprestimo FOREIGN KEY (emprestimo_id) REFERENCES emprestimo(id)
);

-- DADOS DE EXEMPLO
INSERT INTO autor (nome, nacionalidade) VALUES
('Machado de Assis', 'Brasileira'),
('Antoine de Saint-Exupéry', 'Francesa');

INSERT INTO editora (nome, cidade) VALUES
('Editora Exemplo', 'Manaus'),
('Companhia das Letras', 'São Paulo');

INSERT INTO aluno (nome, matricula, email, telefone) VALUES
('Ana Silva', '2026001', 'ana@email.com', '(92) 99999-1111'),
('João Santos', '2026002', 'joao@email.com', '(92) 99999-2222');

INSERT INTO livro (titulo, isbn, categoria, ano_publicacao, quantidade_total, quantidade_disponivel, autor_id, editora_id)
SELECT 'Dom Casmurro', '9780000000001', 'Romance', 1899, 3, 3, a.id, e.id
FROM autor a, editora e
WHERE a.nome='Machado de Assis' AND e.nome='Editora Exemplo'
LIMIT 1;