package com.restaurante.service;

import java.util.List;
import com.restaurante.entity.Mesa;

public interface MesaService {
	List<Mesa> listar();
	Mesa guardar(Mesa mesa);
	Mesa obtenerPorId(Integer id);
	void eliminar(Integer id);
}
