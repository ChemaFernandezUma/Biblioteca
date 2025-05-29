package com.example.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Libro {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column
	    private Long id;
	    @Column
	    private String titulo;
	    @Column
	    private TipoLibro tipoLibro;
	    @Column
	    private String editorial;
	    @Column
	    private int anyo;
	    @OneToMany(mappedBy = "libro")
	    private List<Copia> copias;
	    @ManyToOne
	    @JoinColumn(name = "autor_id")
	    private Autor autor;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public TipoLibro getTipoLibro() {
			return tipoLibro;
		}
		public void setTipoLibro(TipoLibro tipoLibro) {
			this.tipoLibro = tipoLibro;
		}
		public String getEditorial() {
			return editorial;
		}
		public void setEditorial(String editorial) {
			this.editorial = editorial;
		}
		public int getAnyo() {
			return anyo;
		}
		public void setAnyo(int anyo) {
			this.anyo = anyo;
		}
		public List<Copia> getCopias() {
			return copias;
		}
		public void setCopias(List<Copia> copias) {
			this.copias = copias;
		}
	    
	    
}
