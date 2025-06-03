package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.model.Copia;
import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.repository.PrestamoRepository;

@Service
public class PrestamoService {
    private final PrestamoRepository repository;

    @Autowired
    public PrestamoService(PrestamoRepository repository) {
        this.repository = repository;
    }

    public List<Prestamo> getAllPrestamos() {
        return repository.findAll();
    }

    public Optional<Prestamo> getPrestamoById(Long id) {
        return repository.findById(id);
    }

    public Prestamo createPrestamo(Prestamo prestamo) {
        return repository.save(prestamo);
    }

    public Prestamo updatePrestamo(Long id, Prestamo updatedPrestamo) {
        return repository.findById(id).map(prestamo -> {
            prestamo.setInicio(updatedPrestamo.getInicio());
            prestamo.setFin(updatedPrestamo.getFin());
            prestamo.setCopia(updatedPrestamo.getCopia());
            prestamo.setUsuario(updatedPrestamo.getUsuario());
            return repository.save(prestamo);
        }).orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id " + id));
    }

    public void deletePrestamo(Long id) {
        repository.deleteById(id);
    }
    
    // public List<Prestamo> getPrestamosOfUser(Long id) {
    // 	return repository.findByLectorNSocio(id);
    // }
}
