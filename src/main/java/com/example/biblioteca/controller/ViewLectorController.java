package com.example.biblioteca.controller;

import com.example.biblioteca.model.Copia;
import com.example.biblioteca.model.Lector;
import com.example.biblioteca.model.Libro;
import com.example.biblioteca.service.CopiaService;
import com.example.biblioteca.service.LectorService;
import com.example.biblioteca.service.LibroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/lectoresView") // Ruta para vistas
public class ViewLectorController {

    private final LectorService lectorService;

    @Autowired
    public ViewLectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }
    
    @Autowired
    private CopiaService copiaService;
    @Autowired
    private LibroService libroService;

    // 📖 Mostrar lista de lectores
    @GetMapping
    public String mostrarLectores(Model model) {
        model.addAttribute("lectores", lectorService.getAllLectores());
        return "lista-lectores"; // Carga la vista Thymeleaf
    }

    // 📖 Mostrar detalles de un lector por ID
    @GetMapping("/{id}")
    public String mostrarLectorPorId(@PathVariable Long id, Model model) {
        Optional<Lector> lector = lectorService.getLectorById(id);
        if (lector.isPresent()) {
            model.addAttribute("lector", lector.get());
            return "lector-id"; // Carga la vista de detalles del lector
        }
        return "redirect:/lectoresView"; // Redirige si no se encuentra
    }

    // 📖 Mostrar formulario para nuevo lector
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoLector(Model model) {
        model.addAttribute("lector", new Lector());
        return "nuevo-lector"; // Carga la vista del formulario
    }

    // 📖 Guardar un nuevo lector
    @PostMapping("/guardar")
    public String guardarLector(@ModelAttribute Lector lector) {
        lectorService.createLector(lector);
        return "redirect:/lectoresView"; // Redirige a la lista
    }

    // 📖 Mostrar formulario para actualizar un lector
    @GetMapping("/update/{id}")
    public String mostrarFormularioActualizarLector(@PathVariable Long id, Model model) {
        Optional<Lector> lector = lectorService.getLectorById(id);
        if (lector.isPresent()) {
            model.addAttribute("lector", lector.get());
            return "update-lector"; // Carga la vista de actualización
        }
        return "redirect:/lectoresView";
    }

    // 📖 Actualizar un lector existente
    @PostMapping("/actualizar")
    public String actualizarLector(@ModelAttribute Lector lector) {
        lectorService.updateLector(lector.getnSocio(), lector);
        return "redirect:/lectoresView";
    }

    // 📖 Mostrar formulario de confirmación antes de eliminar un lector
    @GetMapping("/delete/{id}")
    public String mostrarConfirmacionEliminar(@PathVariable Long id, Model model) {
        Optional<Lector> lector = lectorService.getLectorById(id);
        if (lector.isPresent()) {
            model.addAttribute("lector", lector.get());
            return "delete-lector"; // Carga la vista de confirmación
        }
        return "redirect:/lectoresView";
    }

    // 📖 Eliminar un lector (Usando `POST` en lugar de `DELETE`)
    @PostMapping("/eliminar")
    public String eliminarLector(@ModelAttribute Lector lector) {
        lectorService.deleteLector(lector.getnSocio());
        return "redirect:/lectoresView";
    }
    
    @GetMapping("/prestar/{id}")
    public String mostrarFormularioPrestar(@PathVariable Long id, Model model) {
        Optional<Lector> lector = lectorService.getLectorById(id);
        if (lector.isPresent()) {
            model.addAttribute("lector", lector.get());
            model.addAttribute("libros", libroService.getAllBooks());
            
            return "prestar-copia";
        }
        return "redirect:/lectoresView";
    }
    
    @PostMapping("/prestar")
    public String prestarCopia(@RequestParam Long libroId, @RequestParam Long lectorId, @RequestParam LocalDate fechaActual) {
        Optional<Lector> lectorOpt = lectorService.getLectorById(lectorId);
        Optional<Libro> copiaOpt = libroService.getLibroById(libroId);

        if (lectorOpt.isPresent() && copiaOpt.isPresent()) {
            Lector lector = lectorOpt.get();
            Libro libro = copiaOpt.get();
            lectorService.prestar(lectorId, libro);
        }
        return "redirect:/lectoresView";
    }
    
    @GetMapping("/devolver/{id}")
    public String mostrarFormularioDevolver(@PathVariable Long id, Model model) {
        Optional<Lector> lector = lectorService.getLectorById(id);
        if (lector.isPresent()) {
            model.addAttribute("lector", lector.get());
            model.addAttribute("copias", copiaService.getAllCopias());
            return "devolver-copia";
        }
        return "redirect:/lectoresView";
    }
 
    
    @PostMapping("/devolver")
    public String devolverCopia(@RequestParam Long copiaId, @RequestParam Long lectorId, @RequestParam LocalDate fechaDevolucion) {
        Optional<Lector> lectorOpt = lectorService.getLectorById(lectorId);
        Optional<Copia> copiaOpt = copiaService.getCopiaById(copiaId);

        if (lectorOpt.isPresent() && copiaOpt.isPresent()) {
            Lector lector = lectorOpt.get();
            Copia copia = copiaOpt.get();
            lector.devolverCopia(copia, fechaDevolucion);
        }
        return "redirect:/lectoresView";
    }



}