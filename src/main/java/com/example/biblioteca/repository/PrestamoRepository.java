package com.example.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
