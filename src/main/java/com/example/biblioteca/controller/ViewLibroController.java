package com.example.biblioteca.controller;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/librosView") // Ruta para vistas
public class ViewLibroController {

    private final LibroService libroService;

    @Autowired
    public ViewLibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public String mostrarLibros(Model model) {
        model.addAttribute("libros", libroService.getAllBooks());
        return "lista-libros"; // Carga la plantilla Thymeleaf
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "nuevo-libro"; // Carga la vista del formulario
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro) {
        libroService.createLibro(libro);
        return "redirect:/librosView"; // Redirige de vuelta a la lista
    }
}
