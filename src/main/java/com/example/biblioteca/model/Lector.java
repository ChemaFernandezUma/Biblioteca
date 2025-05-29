package com.example.biblioteca.model;

import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "lector")  // opcional
public class Lector {
 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long nSocio;
    @Column
    private String nombre;
    @Column
    private String telefono;
    @Column
    private String direccion;
    @OneToMany(mappedBy = "usuario")  // Relación con Prestamo
    private List<Prestamo> prestamos;
}
