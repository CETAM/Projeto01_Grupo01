package br.com.cetam.biblioteca.controller;

import br.com.cetam.biblioteca.entity.Autor;
import br.com.cetam.biblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int pagina, Model model) {
        model.addAttribute("pagina", service.listar(pagina));
        return "autores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("autor", service.buscar(id));
        return "autores/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("autor") Autor autor,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) return "autores/formulario";
        service.salvar(autor);
        redirect.addFlashAttribute("sucesso", "Autor salvo(a) com sucesso!");
        return "redirect:/autores";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.excluir(id);
            redirect.addFlashAttribute("sucesso", "Autor excluído(a) com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir. Verifique se existem registros relacionados.");
        }
        return "redirect:/autores";
    }
}
