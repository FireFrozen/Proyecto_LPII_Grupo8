package com.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.entity.DetallePedido;
import com.restaurante.repository.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

	@Autowired
	private DetallePedidoRepository repoDetallePedido;

	@Override
	public List<DetallePedido> listar() {
		return repoDetallePedido.findAll();
	}

	@Override
	public void registrarDetallePedido(DetallePedido detallePedido) {
		repoDetallePedido.save(detallePedido);
	}

	@Override
	public DetallePedido buscarPorId(int id) {
		return repoDetallePedido.findById(id).orElse(null);
	}

	@Override
	public void actualizarDetallePedido(DetallePedido detallePedido) {
		repoDetallePedido.save(detallePedido);
	}

	@Override
	public void eliminarDetallePedido(int id) {
		repoDetallePedido.deleteById(id);
	}
}
