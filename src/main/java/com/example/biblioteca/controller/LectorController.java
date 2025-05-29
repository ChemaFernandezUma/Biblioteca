package com.example.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.biblioteca.model.Lector;
import com.example.biblioteca.service.LectorService;

@RestController
@RequestMapping("/lectores")
public class LectorController {

	private final LectorService service;

	@Autowired
	public LectorController(LectorService service) {
		this.service = service;
	}

	@GetMapping("/getAll")
	public List<Lector> getAllLectores() {
		return service.getAllLectores();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Lector> getLectorById(@PathVariable Long id) {
		Optional<Lector> lector = service.getLectorById(id);
		return lector.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public ResponseEntity<Lector> createLector(@RequestBody Lector lector) {
		Lector createdLector = service.createLector(lector);
		return ResponseEntity.ok(createdLector);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Lector> updateLector(@PathVariable Long id, @RequestBody Lector lectorDetails) {
		Optional<Lector> updatedLector = service.updateLector(id, lectorDetails);
		return updatedLector.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteLector(@PathVariable Long id) {
		boolean deleted = service.deleteLector(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
