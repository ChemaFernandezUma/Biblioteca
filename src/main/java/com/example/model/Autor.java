package com.example.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class Autor {
 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String nombre;
    @Column
    private String nacionalidad;
    @Column
    private Date fechaNacimiento;
    @OneToMany(mappedBy = "autor")
    private List<Libro> librosEscritos;
    
}
