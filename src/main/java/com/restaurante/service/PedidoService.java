package com.restaurante.service;

import java.util.List;

import com.restaurante.entity.Pedido;

public interface PedidoService {
	List<Pedido> listar();
	void registrarPedido(Pedido pedido);
	Pedido buscarPorId(int id);
	void actualizarPedido(Pedido pedido);
	void desactivarEstadoPedido(int id);
}
