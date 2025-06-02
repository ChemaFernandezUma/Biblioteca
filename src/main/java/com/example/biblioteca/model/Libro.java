package com.example.biblioteca.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column
	    private Long id;

	    @Column
	    private String titulo;

	    @Column
		@Enumerated(EnumType.STRING)
	    private TipoLibro tipoLibro;

	    @Column
	    private String editorial;

	    @Column
	    private int anyo;

	    @OneToMany(mappedBy = "libro")
	    @JsonManagedReference(value = "libro-copias")
	    private List<Copia> copias;

	    @ManyToOne
		@JsonBackReference(value = "autor-libro")
	    @JoinColumn(name = "autor_id")
	    private Autor autor;


		public Libro() {}

		public Libro(Long id, String titulo, TipoLibro tipoLibro, String editorial, int anyo, List<Copia> copias, Autor autor) {
			this.id = id;
			this.titulo = titulo;
			this.tipoLibro = tipoLibro;
			this.editorial = editorial;
			this.anyo = anyo;
			this.copias = copias;
			this.autor = autor;
		}
	    
	    public Copia buscarCopiaDisponible() {
	        return getCopias().stream()
	            .filter(copia -> EstadoCopia.BIBLIOTECA.equals(copia.getEstado()))
	            .findFirst()
	            .orElse(null);
	    }

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

		public Autor getAutor() {
			return autor;
		}

		public void setAutor(Autor autor) {
			this.autor = autor;
		}
}
