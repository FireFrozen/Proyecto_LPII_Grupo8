package com.restaurante.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurante.entity.Plato;
import com.restaurante.repository.PlatoRepository;

@Service
public class PlatoServiceImpl implements PlatoService {

	@Autowired
	private PlatoRepository platoRepo;

	@Override
	public List<Plato> listar() {
		return platoRepo.findAll();
	}

	@Override
	public Plato guardar(Plato plato) {
		if (plato.getIdPlato() == null || plato.getIdPlato() == 0) {
			plato.setEstadoRegistro("ACTIVO");
		} else {
			Plato existente = platoRepo.findById(plato.getIdPlato()).orElse(null);
			if (existente != null) {
				existente.setNombre(plato.getNombre());
				existente.setDescripcion(plato.getDescripcion());
				existente.setPrecio(plato.getPrecio());
				existente.setStock(plato.getStock());
				existente.setCategoria(plato.getCategoria());
				return platoRepo.save(existente);
			}
		}
		return platoRepo.save(plato);
	}

	@Override
	public Plato obtenerPorId(Integer id) {
		return platoRepo.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		Plato plato = platoRepo.findById(id).orElse(null);
		if (plato != null) {
			plato.setEstadoRegistro("INACTIVO");
			platoRepo.save(plato);
		}
	}
}
