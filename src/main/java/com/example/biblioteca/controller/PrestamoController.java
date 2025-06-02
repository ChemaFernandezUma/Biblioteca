package com.example.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.service.PrestamoService;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
	private final PrestamoService service;

	@Autowired
	public PrestamoController(PrestamoService service) {
		this.service = service;
	}

	@GetMapping("/getAll")
	public List<Prestamo> getAllPrestamos() {
		return service.getAllPrestamos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Prestamo> getPrestamoById(@PathVariable Long id) {
		return service.getPrestamoById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public Prestamo createPrestamo(@RequestBody Prestamo prestamo) {
		if (prestamo.getCopia() == null || prestamo.getUsuario() == null) {
			throw new IllegalArgumentException("Copia y Usuario no pueden ser null");
		}
		return service.createPrestamo(prestamo);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Prestamo> updatePrestamo(@PathVariable Long id, @RequestBody Prestamo prestamo) {
		try {
			if (prestamo.getCopia() == null || prestamo.getUsuario() == null) {
				throw new IllegalArgumentException("Copia y Usuario no pueden ser null");
			}
			Prestamo updated = service.updatePrestamo(id, prestamo);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePrestamo(@PathVariable Long id) {
		service.deletePrestamo(id);
		return ResponseEntity.noContent().build();
	}
}
