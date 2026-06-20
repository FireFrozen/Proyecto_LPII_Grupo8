package com.restaurante.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa", nullable = false)
    private Integer idMesa;

    @Column(name = "numero_mesa", nullable = false)
    private int numeroMesa;

    @Column(name = "capacidad", nullable = false)
    private int capacidad;

    @Column(name = "estado_mesa", nullable = false, length = 15)
    private String estadoMesa;

    @Column(name = "estado_registro", nullable = false, length = 10)
    private String estadoRegistro;
    
    @OneToMany(mappedBy = "mesa")
    @JsonIgnore
    private List<Pedido> listaPedidos;

    public Mesa() {
    }

	public Integer getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Integer idMesa) {
		this.idMesa = idMesa;
	}

	public int getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getEstadoMesa() {
		return estadoMesa;
	}

	public void setEstadoMesa(String estadoMesa) {
		this.estadoMesa = estadoMesa;
	}

	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

}