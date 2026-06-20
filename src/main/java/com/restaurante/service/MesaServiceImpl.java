package com.restaurante.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurante.entity.Mesa;
import com.restaurante.repository.MesaRepository;

@Service
public class MesaServiceImpl implements MesaService {

	@Autowired
	private MesaRepository mesaRepo;

	@Override
	public List<Mesa> listar() {
		return mesaRepo.findAll();
	}

	@Override
	public Mesa guardar(Mesa mesa) {
		if (mesa.getIdMesa() == null || mesa.getIdMesa() == 0) {
			mesa.setEstadoRegistro("ACTIVO");
		} else {
			Mesa existente = mesaRepo.findById(mesa.getIdMesa()).orElse(null);
			if (existente != null) {
				existente.setNumeroMesa(mesa.getNumeroMesa());
				existente.setCapacidad(mesa.getCapacidad());
				existente.setEstadoMesa(mesa.getEstadoMesa());
				return mesaRepo.save(existente);
			}
		}
		return mesaRepo.save(mesa);
	}

	@Override
	public Mesa obtenerPorId(Integer id) {
		return mesaRepo.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		Mesa mesa = mesaRepo.findById(id).orElse(null);
		if (mesa != null) {
			mesa.setEstadoRegistro("INACTIVO");
			mesaRepo.save(mesa);
		}
	}
}
