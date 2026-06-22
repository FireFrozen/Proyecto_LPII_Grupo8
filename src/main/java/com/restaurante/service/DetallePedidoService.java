package com.restaurante.service;

import java.util.List;

import com.restaurante.entity.DetallePedido;

public interface DetallePedidoService {
	List<DetallePedido> listar();
	void registrarDetallePedido(DetallePedido detallePedido);
	DetallePedido buscarPorId(int id);
	void actualizarDetallePedido(DetallePedido detallePedido);
	void eliminarDetallePedido(int id);
}
