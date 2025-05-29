package com.example.model;

import jakarta.persistence.*;

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
    @OneToOne(mappedBy = "copia")  // Mapeo inverso de Prestamo
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
    
}
