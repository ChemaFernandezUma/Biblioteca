package com.example.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // Ruta base en localhost:8080/
public class HomeController {

    @GetMapping
    public String mostrarPaginaPrincipal(Model model) {
        return "home"; // Carga la vista de selección
    }
}