package com.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.entity.Pedido;
import com.restaurante.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository repoPedido;

	@Override
	public List<Pedido> listar() {
		return repoPedido.findAll();
	}

	@Override
	public void registrarPedido(Pedido pedido) {
		repoPedido.save(pedido);
	}

	@Override
	public Pedido buscarPorId(int id) {
		return repoPedido.findById(id).orElse(null);
	}

	@Override
	public void actualizarPedido(Pedido pedido) {
		repoPedido.save(pedido);
	}

	@Override
	public void desactivarEstadoPedido(int id) {
		Pedido pedido = buscarPorId(id);
		if (pedido != null) {
			pedido.setEstadoPedido("INACTIVO");
			repoPedido.save(pedido);
		}
	}
}
