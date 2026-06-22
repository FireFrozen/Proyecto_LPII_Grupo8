package com.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurante.entity.Cliente;
import com.restaurante.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repoCliente;

	@Override
	public List<Cliente> listar() {
		return repoCliente.findAll();
	}

	@Override
	public void registrarCliente(Cliente cliente) {
		repoCliente.save(cliente);
	}

	@Override
	public Cliente buscarPorId(int id) {
		return repoCliente.findById(id).orElse(null);
	}

	@Override
	public void actualizarCliente(Cliente cliente) {
		repoCliente.save(cliente);
	}

	@Override
	public void desactivarEstadoCliente(int id) {
	Cliente cliente = buscarPorId(id);
		if(cliente != null) {
			cliente.setEstadoRegistro("INACTIVO");
			repoCliente.save(cliente);
		}

	}

}