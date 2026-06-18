package com.restaurante.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pedido", nullable = false)
    private int idDetallePedido;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @JsonIgnoreProperties({"listaDetalles"})
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_plato")
    @JsonIgnoreProperties({"listaDetalles"})
    private Plato plato;

    public DetallePedido() {
    }

	public int getIdDetallePedido() {
		return idDetallePedido;
	}

	public void setIdDetallePedido(int idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}
}