package com.example.biblioteca.service;

import java.util.List;

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
    
    public List<Libro> getAllBooks() {
    	return repository.findAll(); 
    }
}
