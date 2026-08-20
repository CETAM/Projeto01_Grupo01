package br.com.cetam.biblioteca.controller;

import br.com.cetam.biblioteca.service.RelatorioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {
    private final RelatorioService service;
    public RelatorioController(RelatorioService service) { this.service = service; }

    @GetMapping
    public String relatorios(Model model) {
        model.addAttribute("resumo", service.resumo());
        return "relatorios/index";
    }
}
