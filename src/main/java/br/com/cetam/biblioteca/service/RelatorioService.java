package br.com.cetam.biblioteca.service;

import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RelatorioService {
    private final LivroService livroService;
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final AlunoService alunoService;
    private final EmprestimoService emprestimoService;
    private final MultaService multaService;

    public RelatorioService(LivroService livroService, AutorService autorService, EditoraService editoraService,
                            AlunoService alunoService, EmprestimoService emprestimoService, MultaService multaService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.editoraService = editoraService;
        this.alunoService = alunoService;
        this.emprestimoService = emprestimoService;
        this.multaService = multaService;
    }

    public Map<String, Long> resumo() {
        Map<String, Long> dados = new LinkedHashMap<>();
        dados.put("Livros cadastrados", livroService.contar());
        dados.put("Livros com disponibilidade", livroService.contarDisponiveis());
        dados.put("Autores", autorService.contar());
        dados.put("Editoras", editoraService.contar());
        dados.put("Alunos", alunoService.contar());
        dados.put("Empréstimos ativos", emprestimoService.contarAtivos());
        dados.put("Empréstimos atrasados", emprestimoService.contarAtrasados());
        dados.put("Multas pendentes", multaService.contarPendentes());
        return dados;
    }
}
