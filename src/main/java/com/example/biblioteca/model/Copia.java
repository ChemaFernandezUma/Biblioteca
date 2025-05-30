package com.example.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "copia")
public class Copia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private EstadoCopia estado;

	@ManyToOne
	@JoinColumn(name = "libro_id")
	@JsonBackReference(value = "libro-copias")
	private Libro libro;

	@OneToOne(mappedBy = "copia")
	@JsonBackReference(value = "copia-prestamo")
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
