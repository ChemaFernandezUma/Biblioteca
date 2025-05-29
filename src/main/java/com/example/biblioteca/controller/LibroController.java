package com.example.biblioteca.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.service.LibroService;

@RestController
@RequestMapping("/books")
public class LibroController {
	private LibroService service;

	@Autowired
	public LibroController(LibroService service) {
		this.service = service;
	}
	
	@GetMapping("/getAll")
	public List<Libro> getAllBooks(){
		return service.getAllBooks();
	}
}
