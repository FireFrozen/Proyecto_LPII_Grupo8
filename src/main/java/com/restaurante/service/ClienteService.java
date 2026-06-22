package com.restaurante.service;

import java.util.List;
import com.restaurante.entity.Cliente;

public interface ClienteService {
	List<Cliente> listar();
	void registrarCliente(Cliente cliente);
	Cliente buscarPorId(int id);
	void actualizarCliente(Cliente cliente);
	void desactivarEstadoCliente(int id);
}
