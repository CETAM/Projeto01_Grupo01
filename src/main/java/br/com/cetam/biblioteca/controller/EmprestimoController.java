package br.com.cetam.biblioteca.controller;

import br.com.cetam.biblioteca.entity.Emprestimo;
import br.com.cetam.biblioteca.service.AlunoService;
import br.com.cetam.biblioteca.service.EmprestimoService;
import br.com.cetam.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService service;
    private final LivroService livroService;
    private final AlunoService alunoService;

    public EmprestimoController(EmprestimoService service, LivroService livroService, AlunoService alunoService) {
        this.service = service;
        this.livroService = livroService;
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int pagina, Model model) {
        model.addAttribute("pagina", service.listar(pagina));
        return "emprestimos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        preparar(model, new Emprestimo());
        return "emprestimos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        preparar(model, service.buscar(id));
        return "emprestimos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("emprestimo") Emprestimo emprestimo,
                         BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            preparar(model, emprestimo);
            return "emprestimos/formulario";
        }
        try {
            service.salvar(emprestimo);
            redirect.addFlashAttribute("sucesso", "Empréstimo salvo com sucesso!");
            return "redirect:/emprestimos";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            preparar(model, emprestimo);
            return "emprestimos/formulario";
        }
    }

    @PostMapping("/devolver/{id}")
    public String devolver(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.devolver(id);
            redirect.addFlashAttribute("sucesso", "Devolução registrada com sucesso. Se houve atraso, a multa foi gerada.");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/emprestimos";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.excluir(id);
            redirect.addFlashAttribute("sucesso", "Empréstimo excluído com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir o empréstimo.");
        }
        return "redirect:/emprestimos";
    }

    private void preparar(Model model, Emprestimo emprestimo) {
        model.addAttribute("emprestimo", emprestimo);
        model.addAttribute("livros", livroService.listarTodos());
        model.addAttribute("alunos", alunoService.listarTodos());
    }
}
