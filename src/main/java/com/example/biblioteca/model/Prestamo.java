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
    private Copia copia;
    @ManyToOne
    @JoinColumn(name = "lector_nSocio") // Clave foránea hacia el usuario
    private Lector usuario;
    
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
	public Copia getCopia() {
		return copia;
	}
	public void setCopia(Copia copia) {
		this.copia = copia;
	}
	public Lector getUsuario() {
		return usuario;
	}
	public void setUsuario(Lector usuario) {
		this.usuario = usuario;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    
}
