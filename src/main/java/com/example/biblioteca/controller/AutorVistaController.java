package com.example.biblioteca.controller;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autores") // <- Rutas para el frontend Thymeleaf
public class AutorVistaController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public String listarAutores(Model model) {
        model.addAttribute("autores", autorService.getAllAutores());
        return "autores/lista"; // Carga templates/autores/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/formulario";
    }

    @PostMapping("/guardar")
    public String guardarAutor(@ModelAttribute Autor autor) {
        autorService.createAutor(autor);
        return "redirect:/autores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Autor autor = autorService.getAutorById(id)
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado: " + id));
        model.addAttribute("autor", autor);
        return "autores/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
        return "redirect:/autores";
    }
}
