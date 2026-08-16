# Modelo Relacional

- **AUTOR** (`id` PK, nome, nacionalidade)
- **EDITORA** (`id` PK, nome, cidade)
- **ALUNO** (`id` PK, nome, matricula UNIQUE, email, telefone)
- **LIVRO** (`id` PK, titulo, isbn UNIQUE, categoria, ano_publicacao, quantidade_total, quantidade_disponivel, `autor_id` FK, `editora_id` FK)
- **EMPRESTIMO** (`id` PK, `livro_id` FK, `aluno_id` FK, data_emprestimo, data_prevista_devolucao, data_devolucao, status)
- **MULTA** (`id` PK, `emprestimo_id` FK UNIQUE, dias_atraso, valor, status, data_geracao, data_pagamento)

## Relacionamentos

- Autor 1:N Livro
- Editora 1:N Livro
- Aluno 1:N Empréstimo
- Livro 1:N Empréstimo
- Empréstimo 1:0..1 Multa
