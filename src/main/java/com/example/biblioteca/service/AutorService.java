package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.repository.AutorRepository;

@Service
public class AutorService {

	private final AutorRepository repository;

	@Autowired
	public AutorService(AutorRepository repository) {
		this.repository = repository;
	}

	public List<Autor> getAllAutores() {
		return repository.findAll();
	}

	public Optional<Autor> getAutorById(Long id) {
		return repository.findById(id);
	}

	public Autor createAutor(Autor autor) {
		return repository.save(autor);
	}

	public Optional<Autor> updateAutor(Long id, Autor autorDetails) {
		return repository.findById(id).map(autor -> {
			autor.setNombre(autorDetails.getNombre());
			autor.setNacionalidad(autorDetails.getNacionalidad());
			autor.setFechaNacimiento(autorDetails.getFechaNacimiento());
			return repository.save(autor);
		});
	}

	public boolean deleteAutor(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
