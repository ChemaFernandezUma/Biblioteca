package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.model.Lector;
import com.example.biblioteca.repository.LectorRepository;

@Service
public class LectorService {

    private final LectorRepository repository;

    @Autowired
    public LectorService(LectorRepository repository) {
        this.repository = repository;
    }

    public List<Lector> getAllLectores() {
        return repository.findAll();
    }

    public Optional<Lector> getLectorById(Long id) {
        return repository.findById(id);
    }

    public Lector createLector(Lector lector) {
        return repository.save(lector);
    }

    public Optional<Lector> updateLector(Long id, Lector lectorDetails) {
        return repository.findById(id).map(lector -> {
            lector.setNombre(lectorDetails.getNombre());
            lector.setTelefono(lectorDetails.getTelefono());
            lector.setDireccion(lectorDetails.getDireccion());
            return repository.save(lector);
        });
    }

    public boolean deleteLector(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
