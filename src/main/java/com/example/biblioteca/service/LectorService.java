package com.example.biblioteca.service;


import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.model.Lector;
import com.example.biblioteca.model.Libro;
import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.repository.CopiaRepository;
import com.example.biblioteca.repository.LectorRepository;
import com.example.biblioteca.model.Copia;
import com.example.biblioteca.model.EstadoCopia;
import com.example.biblioteca.model.Multa;

@Service
public class LectorService {

    private final LectorRepository repository;
    private final CopiaRepository copiaRepository;

    @Autowired
    public LectorService(LectorRepository repository, CopiaRepository copiaRepository) {
        this.repository = repository;
        this.copiaRepository = copiaRepository;
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

    public boolean devolver(Long id, Copia copia) {
        if (copia == null) {
            throw new IllegalArgumentException("La copia no puede ser null");
        }

        Optional<Lector> lectorOpt = repository.findById(id);
        if (lectorOpt.isPresent()) {
            Lector lector = lectorOpt.get();
            Optional<Prestamo> prestamoOpt = lector.getPrestamos().stream()
                .filter(prestamo -> prestamo.getCopia().getId().equals(copia.getId())) // Actualització aquí
                .findFirst();

            if (prestamoOpt.isPresent()) {
                Prestamo prestamo = prestamoOpt.get();
                lector.getPrestamos().remove(prestamo);
                copia.setEstado(EstadoCopia.BIBLIOTECA);
                repository.save(lector);
                copiaRepository.save(copia);
                return true;
            }
        }
        return false;
    }

    public boolean prestar(Long id, Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser null");
        }

        Optional<Lector> lectorOpt = repository.findById(id);
        if (lectorOpt.isPresent()) {
            Lector lector = lectorOpt.get();
            if (!lector.tieneMultaActiva() && lector.getPrestamos().size() < 3) {
                Copia copia = libro.buscarCopiaDisponible();
                if (copia != null) {
                    Date fechaAct = Date.valueOf(LocalDate.now());
                    Date fechaFin = new Date(fechaAct.getTime() + (30L * 24 * 60 * 60 * 1000));
                    Prestamo prestamo = new Prestamo(fechaAct, fechaFin, copia, lector); // Actualització aquí
                    copia.setEstado(EstadoCopia.PRESTADO);
                    lector.getPrestamos().add(prestamo);
                    repository.save(lector);
                    copiaRepository.save(copia);
                    return true;
                }
            }
        }
        return false;
    }

    public void multar(Long id, int diasRetraso) {
        if (diasRetraso <= 0) {
            throw new IllegalArgumentException("Los días de retraso deben ser mayores a 0");
        }

        Optional<Lector> lectorOpt = repository.findById(id);
        if (lectorOpt.isPresent()) {
            Lector lector = lectorOpt.get();
            LocalDate inicio = LocalDate.now();
            lector.setMulta(new Multa(inicio, inicio.plusDays(diasRetraso * 2)));
            repository.save(lector);
        } else {
            throw new IllegalArgumentException("El lector con ID " + id + " no existe");
        }
    }
}
