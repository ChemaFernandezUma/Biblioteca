package com.example.biblioteca.model;

import java.time.LocalDate;


public class Multa {
    private LocalDate fInicio;
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