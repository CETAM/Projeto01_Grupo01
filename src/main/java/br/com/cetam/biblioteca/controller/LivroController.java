package br.com.cetam.biblioteca.controller;

import br.com.cetam.biblioteca.entity.Livro;
import br.com.cetam.biblioteca.service.AutorService;
import br.com.cetam.biblioteca.service.EditoraService;
import br.com.cetam.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivroService service;
    private final AutorService autorService;
    private final EditoraService editoraService;

    public LivroController(LivroService service, AutorService autorService, EditoraService editoraService) {
        this.service = service;
        this.autorService = autorService;
        this.editoraService = editoraService;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int pagina, Model model) {
        model.addAttribute("pagina", service.listar(pagina));
        return "livros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        prepararFormulario(model, new Livro());
        return "livros/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        prepararFormulario(model, service.buscar(id));
        return "livros/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("livro") Livro livro,
                         BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            prepararFormulario(model, livro);
            return "livros/formulario";
        }
        service.salvar(livro);
        redirect.addFlashAttribute("sucesso", "Livro salvo com sucesso!");
        return "redirect:/livros";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.excluir(id);
            redirect.addFlashAttribute("sucesso", "Livro excluído com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir este livro. Pode haver empréstimos relacionados.");
        }
        return "redirect:/livros";
    }

    private void prepararFormulario(Model model, Livro livro) {
        model.addAttribute("livro", livro);
        model.addAttribute("autores", autorService.listarTodos());
        model.addAttribute("editoras", editoraService.listarTodos());
    }
}
