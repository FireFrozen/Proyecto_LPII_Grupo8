package com.restaurante.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_plato")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plato", nullable = false)
    private Integer idPlato;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "estado_registro", nullable = false, length = 10)
    private String estadoRegistro;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    @JsonIgnoreProperties({"listaPlatos"})
    private CategoriaPlato categoria;
    
    @OneToMany(mappedBy = "plato")
    @JsonIgnore
    private List<DetallePedido> listaDetalles;

    public Plato() {
    }

	public Integer getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(Integer idPlato) {
		this.idPlato = idPlato;
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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public CategoriaPlato getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaPlato categoria) {
		this.categoria = categoria;
	}

	public List<DetallePedido> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<DetallePedido> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

}