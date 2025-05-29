package com.example.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.model.Copia;
import com.example.biblioteca.model.Libro;

@Repository
public interface CopiaRepository extends JpaRepository<Copia, Long> {
}