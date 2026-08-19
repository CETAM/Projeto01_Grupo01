package br.com.cetam.biblioteca.controller;

import br.com.cetam.biblioteca.entity.Multa;
import br.com.cetam.biblioteca.service.MultaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/multas")
public class MultaController {
    private final MultaService service;
    public MultaController(MultaService service) { this.service = service; }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int pagina, Model model) {
        model.addAttribute("pagina", service.listar(pagina));
        return "multas/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("multa", service.buscar(id));
        return "multas/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("multa") Multa multa, RedirectAttributes redirect) {
        Multa atual = service.buscar(multa.getId());
        atual.setDiasAtraso(multa.getDiasAtraso());
        atual.setValor(multa.getValor());
        atual.setStatus(multa.getStatus());
        service.salvar(atual);
        redirect.addFlashAttribute("sucesso", "Multa atualizada com sucesso!");
        return "redirect:/multas";
    }

    @PostMapping("/pagar/{id}")
    public String pagar(@PathVariable Long id, RedirectAttributes redirect) {
        service.pagar(id);
        redirect.addFlashAttribute("sucesso", "Multa marcada como paga!");
        return "redirect:/multas";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.excluir(id);
        redirect.addFlashAttribute("sucesso", "Multa excluída com sucesso!");
        return "redirect:/multas";
    }
}
