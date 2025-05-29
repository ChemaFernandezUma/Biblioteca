package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.repository.LibroRepository;

@Service
public class LibroService {

	private final LibroRepository repository;

	@Autowired
	public LibroService(LibroRepository repository) {
		this.repository = repository;
	}

	// Obtener todos los libros
	public List<Libro> getAllBooks() {
		return repository.findAll();
	}

	// Obtener libro por ID
	public Optional<Libro> getLibroById(Long id) {
		return repository.findById(id);
	}

	// Crear un libro nuevo
	public Libro createLibro(Libro libro) {
		return repository.save(libro);
	}

	// Editar (actualizar) un libro existente
	public Optional<Libro> updateLibro(Long id, Libro libroDetails) {
	    return repository.findById(id).map(libro -> {
	        libro.setTitulo(libroDetails.getTitulo());
	        libro.setTipoLibro(libroDetails.getTipoLibro());
	        libro.setEditorial(libroDetails.getEditorial());
	        libro.setAnyo(libroDetails.getAnyo());
	        libro.setAutor(libroDetails.getAutor());
	        // No actualizamos copias aquí porque es una relación OneToMany y usualmente se maneja aparte.
	        return repository.save(libro);
	    });
	}


	public boolean deleteLibro(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
