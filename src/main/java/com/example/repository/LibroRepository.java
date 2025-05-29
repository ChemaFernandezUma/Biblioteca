package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}