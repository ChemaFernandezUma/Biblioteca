package com.example.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.service.LibroService;

@RestController
@RequestMapping("/libros")
public class LibroController {
	private final LibroService service;

	@Autowired
	public LibroController(LibroService service) {
		this.service = service;
	}

	@GetMapping("/getAll")
	public List<Libro> getAllBooks() {
		return service.getAllBooks();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Libro> getBookById(@PathVariable Long id) {
		Optional<Libro> libro = service.getLibroById(id);
		return libro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public ResponseEntity<Libro> createBook(@RequestBody Libro libro) {
		Libro createdLibro = service.createLibro(libro);
		return ResponseEntity.ok(createdLibro);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Libro> updateBook(@PathVariable Long id, @RequestBody Libro libroDetails) {
		Optional<Libro> updatedLibro = service.updateLibro(id, libroDetails);
		return updatedLibro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		boolean deleted = service.deleteLibro(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
