package com.example.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewCopiaController {

    @GetMapping("/copiasView")
    public String mostrarVistaCopias() {
        return "copias"; 
    }
}