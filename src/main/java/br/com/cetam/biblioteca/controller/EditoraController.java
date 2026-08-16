package br.com.cetam.biblioteca.controller;

import br.com.cetam.biblioteca.entity.Editora;
import br.com.cetam.biblioteca.service.EditoraService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraService service;

    public EditoraController(EditoraService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int pagina, Model model) {
        model.addAttribute("pagina", service.listar(pagina));
        return "editoras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("editora", new Editora());
        return "editoras/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("editora", service.buscar(id));
        return "editoras/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("editora") Editora editora,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) return "editoras/formulario";
        service.salvar(editora);
        redirect.addFlashAttribute("sucesso", "Editora salvo(a) com sucesso!");
        return "redirect:/editoras";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.excluir(id);
            redirect.addFlashAttribute("sucesso", "Editora excluído(a) com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir. Verifique se existem registros relacionados.");
        }
        return "redirect:/editoras";
    }
}
