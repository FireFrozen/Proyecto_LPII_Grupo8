package com.restaurante.service;

import java.util.List;

import com.restaurante.entity.Comprobante;

public interface ComprobanteService {
	List<Comprobante> listar();
	void registrarComprobante(Comprobante comprobante);
	Comprobante buscarPorId(int id);
	void actualizarComprobante(Comprobante comprobante);
	void desactivarEstadoComprobante(int id);
}
