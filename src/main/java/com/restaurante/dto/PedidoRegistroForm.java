package com.restaurante.dto;

import java.util.ArrayList;
import java.util.List;

public class PedidoRegistroForm {

	private Integer idCliente;
	private Integer idMesa;
	private Integer idUsuario;
	private List<DetallePedidoRegistroForm> detalles = new ArrayList<>();

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Integer idMesa) {
		this.idMesa = idMesa;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<DetallePedidoRegistroForm> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedidoRegistroForm> detalles) {
		this.detalles = detalles;
	}
}
