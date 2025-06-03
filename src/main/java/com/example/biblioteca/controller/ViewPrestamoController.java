package com.example.biblioteca.controller;

import org.springframework.ui.Model;
import com.example.biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewPrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/prestamosView")
    public String verPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.getAllPrestamos());
        return "prestamo"; // apunta a prestamos.html
    }
}

