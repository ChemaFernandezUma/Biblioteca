package com.example.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Lector;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
}