package com.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.entity.Comprobante;
import com.restaurante.repository.ComprobanteRepository;

@Service
public class ComprobanteServiceImpl implements ComprobanteService {

	@Autowired
	private ComprobanteRepository repoComprobante;

	@Override
	public List<Comprobante> listar() {
		return repoComprobante.findAll();
	}

	@Override
	public void registrarComprobante(Comprobante comprobante) {
		repoComprobante.save(comprobante);
	}

	@Override
	public Comprobante buscarPorId(int id) {
		return repoComprobante.findById(id).orElse(null);
	}

	@Override
	public void actualizarComprobante(Comprobante comprobante) {
		repoComprobante.save(comprobante);
	}

	@Override
	public void desactivarEstadoComprobante(int id) {
		Comprobante comprobante = buscarPorId(id);
		if (comprobante != null) {
			comprobante.setEstadoComprobante("INACTIVO");
			repoComprobante.save(comprobante);
		}
	}
}
