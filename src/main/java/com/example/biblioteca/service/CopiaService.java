package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.model.Copia;
import com.example.biblioteca.repository.CopiaRepository;

@Service
public class CopiaService {

    private final CopiaRepository repository;

    @Autowired
    public CopiaService(CopiaRepository repository) {
        this.repository = repository;
    }

    public List<Copia> getAllCopias() {
        return repository.findAll();
    }

    public Optional<Copia> getCopiaById(Long id) {
        return repository.findById(id);
    }

    public Copia createCopia(Copia copia) {
        return repository.save(copia);
    }

    public Optional<Copia> updateCopia(Long id, Copia copiaDetails) {
        return repository.findById(id).map(copia -> {
            copia.setEstado(copiaDetails.getEstado());
            copia.setLibro(copiaDetails.getLibro());
            return repository.save(copia);
        });
    }

    public boolean deleteCopia(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
