package com.restaurante.service;

import java.util.List;
import com.restaurante.entity.Plato;

public interface PlatoService {
	List<Plato> listar();
	Plato guardar(Plato plato);
	Plato obtenerPorId(Integer id);
	void eliminar(Integer id);
}
