package com.restaurante.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_categoria_plato")
public class CategoriaPlato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private int idCategoria;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "estado_registro", nullable = false, length = 10)
    private String estadoRegistro;
    
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Plato> listaPlatos;

    public CategoriaPlato() {
    }

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public List<Plato> getListaPlatos() {
		return listaPlatos;
	}

	public void setListaPlatos(List<Plato> listaPlatos) {
		this.listaPlatos = listaPlatos;
	}
}