package com.example.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.service.AutorService;

@RestController
@RequestMapping("/autors")
public class AutorController {

	private final AutorService service;

	@Autowired
	public AutorController(AutorService service) {
		this.service = service;
	}

	@GetMapping("/getAll")
	public List<Autor> getAllAutores() {
		return service.getAllAutores();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Autor> getAutorById(@PathVariable Long id) {
		Optional<Autor> autor = service.getAutorById(id);
		return autor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) {
		Autor createdAutor = service.createAutor(autor);
		return ResponseEntity.ok(createdAutor);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Autor> updateAutor(@PathVariable Long id, @RequestBody Autor autorDetails) {
		Optional<Autor> updatedAutor = service.updateAutor(id, autorDetails);
		return updatedAutor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
		boolean deleted = service.deleteAutor(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
