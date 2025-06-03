package com.example.biblioteca.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "lector")
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

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value = "lector-prestamo")
    private List<Prestamo> prestamos;

    @OneToOne(cascade = CascadeType.ALL)
    private Multa multa;

	public Lector() {}

    public Lector(Long nSocio, String nombre, String telefono, String direccion, List<Prestamo> prestamos, Multa multa) {
        this.nSocio = nSocio;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.prestamos = prestamos;
        this.multa = multa;
    }
    
    public boolean tieneMultaActiva() {
        return multa != null && multa.estaActiva();
    }
    public void aplicarMulta(int diasRetraso) {
		if(diasRetraso <=0){
        LocalDate inicio = LocalDate.now();
        this.multa = new Multa(inicio, inicio.plusDays(diasRetraso * 2));
		}
    }
    
    public void devolverCopia(Copia copia, LocalDate fechaDevolucion) {
        Prestamo prestamo = copia.getPrestamo();

        if (prestamo == null || !prestamos.contains(prestamo) || prestamo.getFin() != null) {
            System.out.println("No hay préstamo activo para esta copia.");
            return;
        }

        prestamo.setFin(java.sql.Date.valueOf(fechaDevolucion));

        LocalDate fechaInicio = prestamo.getInicio().toLocalDate();
        long diasPrestamo = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaDevolucion);

        if (diasPrestamo > 30) {
            int diasRetraso = (int) (diasPrestamo - 30);
            this.aplicarMulta(diasRetraso);
            System.out.println("Multa aplicada por " + diasRetraso + " días de retraso.");
        }

        copia.setPrestamo(null);
        copia.setEstado(EstadoCopia.BIBLIOTECA);

        System.out.println("Devolución procesada correctamente.");
    }
    
    public void prestarCopia(Copia copia, LocalDate fechaActual) {
        if (this.tieneMultaActiva()) {
            System.out.println("El lector tiene una multa activa.");
            return;
        }

        long prestamosActivos = prestamos.stream()
            .filter(p -> p.getFin() == null)
            .count();

        if (prestamosActivos >= 3) {
            System.out.println("El lector ya tiene 3 préstamos activos.");
            return;
        }

        if (copia.getEstado() != EstadoCopia.BIBLIOTECA) {
            System.out.println("La copia no está disponible en la biblioteca.");
            return;
        }

        Prestamo nuevoPrestamo = new Prestamo(java.sql.Date.valueOf(fechaActual), null, copia, this);
        prestamos.add(nuevoPrestamo);
        copia.setPrestamo(nuevoPrestamo);
        copia.setEstado(EstadoCopia.PRESTADO);

        System.out.println("Préstamo registrado correctamente.");
    }




	
	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}
	
	public Long getnSocio() {
		return nSocio;
	}

	public void setnSocio(Long nSocio) {
		this.nSocio = nSocio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
}
