package com.example.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "copia") // opcional
public class Copia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column
	private EstadoCopia estado;
	@ManyToOne
	@JoinColumn(name = "libro_id")
	private Libro libro;
	@OneToOne(mappedBy = "copia") // Mapeo inverso de Prestamo
	private Prestamo prestamo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoCopia getEstado() {
		return estado;
	}

	public void setEstado(EstadoCopia estado) {
		this.estado = estado;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

}
