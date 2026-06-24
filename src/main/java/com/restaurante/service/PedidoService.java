package com.restaurante.service;

import java.util.List;

import com.restaurante.dto.PedidoRegistroForm;
import com.restaurante.entity.Pedido;

public interface PedidoService {
	List<Pedido> listar();
	List<Pedido> listarNoPagados();
	Pedido registrarPedido(PedidoRegistroForm pedidoForm);
	Pedido buscarPorId(int id);
	Pedido buscarPorIdConDetalles(int id);
	void actualizarPedido(Pedido pedido);
	boolean actualizarEstadoPedido(int id, String estadoPedido);
	void desactivarEstadoPedido(int id);
}
