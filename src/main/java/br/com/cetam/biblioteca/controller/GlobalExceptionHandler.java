package br.com.cetam.biblioteca.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String integridade(DataIntegrityViolationException ex, HttpServletRequest request, RedirectAttributes redirect) {
        redirect.addFlashAttribute("erro", "Operação não permitida: existe um registro relacionado ou um valor duplicado.");
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}
