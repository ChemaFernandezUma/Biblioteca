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
