package com.example.biblioteca.service;

import com.example.biblioteca.model.Multa;
import com.example.biblioteca.repository.MultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultaService {

    private final MultaRepository multaRepository;

    public MultaService(MultaRepository multaRepository) {
        this.multaRepository = multaRepository;
    }

    public List<Multa> findAll() {
        return multaRepository.findAll();
    }

    public Optional<Multa> findById(Long id) {
        return multaRepository.findById(id);
    }

    public Multa save(Multa multa) {
        return multaRepository.save(multa);
    }

    public void deleteById(Long id) {
        multaRepository.deleteById(id);
    }
}

