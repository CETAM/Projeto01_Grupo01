package br.com.cetam.biblioteca.controller;

import br.com.cetam.biblioteca.service.RelatorioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final RelatorioService relatorioService;
    public HomeController(RelatorioService relatorioService) { this.relatorioService = relatorioService; }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("resumo", relatorioService.resumo());
        return "index";
    }
}
