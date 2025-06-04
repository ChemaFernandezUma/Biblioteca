package com.example.biblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "multa")
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "f_inicio", nullable = false)
    private LocalDate fInicio;

    @Column(name = "f_fin", nullable = false)
    private LocalDate fFin;

    public Multa() {}

    public Multa(LocalDate fInicio, LocalDate fFin) {
        this.fInicio = fInicio;
        this.fFin = fFin;
    }

    public boolean estaActiva() {
        LocalDate hoy = LocalDate.now();
        return (hoy.isEqual(fInicio) || hoy.isAfter(fInicio)) && hoy.isBefore(fFin);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getfInicio() {
        return fInicio;
    }

    public void setfInicio(LocalDate fInicio) {
        this.fInicio = fInicio;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }
}