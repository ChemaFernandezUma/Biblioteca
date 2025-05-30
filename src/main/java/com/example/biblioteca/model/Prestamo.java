package com.example.biblioteca.model;

import java.sql.Date;

import jakarta.persistence.*;
@Entity
@Table(name = "prestamo")  // opcional
public class Prestamo {
 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
	private Long id;
	@Column
	private Date inicio;
	@Column
	private Date fin;
    @OneToOne
    @JoinColumn(name = "copia_id")  // Clave foránea apuntando a una copia
    private Long copia;
    @ManyToOne
    @JoinColumn(name = "lector_nSocio") // Clave foránea hacia el usuario
    private Long usuario;
    
    
	public Prestamo(Date inicio, Date fin, Long copia, Long usuario) {
		this.inicio = inicio;
		this.fin = fin;
		this.copia = copia;
		this.usuario = usuario;
	}
	
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}

	public Long getCopia() {
		return copia;
	}

	public void setCopia(long copia) {
		this.copia = copia;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    
}
