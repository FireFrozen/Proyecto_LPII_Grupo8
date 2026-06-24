package com.restaurante.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.entity.Comprobante;
import com.restaurante.entity.DetallePedido;
import com.restaurante.entity.Pedido;
import com.restaurante.entity.Plato;
import com.restaurante.entity.Usuario;
import com.restaurante.repository.ComprobanteRepository;
import com.restaurante.repository.PedidoRepository;
import com.restaurante.repository.PlatoRepository;
import com.restaurante.repository.UsuarioRepository;

@Service
public class ComprobanteServiceImpl implements ComprobanteService {

	@Autowired
	private ComprobanteRepository repoComprobante;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PlatoRepository platoRepository;

	@Override
	public List<Comprobante> listar() {
		return repoComprobante.findAll();
	}

	@Override
	@Transactional
	public void registrarComprobante(Comprobante comprobante) {
		prepararComprobante(comprobante);
		procesarPagoPedido(comprobante.getPedido());
		comprobante.setIdComprobante(0);
		repoComprobante.save(comprobante);
	}

	@Override
	public Comprobante buscarPorId(int id) {
		return repoComprobante.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void actualizarComprobante(Comprobante comprobante) {
		Comprobante existente = buscarPorId(comprobante.getIdComprobante());
		if (existente == null) {
			throw new IllegalArgumentException("El comprobante no existe.");
		}

		prepararComprobante(comprobante);
		existente.setTipo(comprobante.getTipo());
		existente.setNumero(comprobante.getNumero());
		existente.setFechaEmision(comprobante.getFechaEmision());
		existente.setSubtotal(comprobante.getSubtotal());
		existente.setIgv(comprobante.getIgv());
		existente.setTotal(comprobante.getTotal());
		existente.setEstadoComprobante(comprobante.getEstadoComprobante());
		existente.setPedido(comprobante.getPedido());
		existente.setUsuario(comprobante.getUsuario());
		repoComprobante.save(existente);
	}

	@Override
	@Transactional
	public void desactivarEstadoComprobante(int id) {
		Comprobante comprobante = buscarPorId(id);
		if (comprobante != null) {
			comprobante.setEstadoComprobante("ANULADO");
			repoComprobante.save(comprobante);
		}
	}

	private void prepararComprobante(Comprobante comprobante) {
		if (comprobante.getPedido() == null || comprobante.getUsuario() == null) {
			throw new IllegalArgumentException("El pedido y el cajero son obligatorios.");
		}

		Pedido pedido = pedidoRepository.findById(comprobante.getPedido().getIdPedido())
				.orElseThrow(() -> new IllegalArgumentException("El pedido seleccionado no existe."));
		Usuario usuario = usuarioRepository.findById(comprobante.getUsuario().getIdUsuario())
				.orElseThrow(() -> new IllegalArgumentException("El usuario seleccionado no existe."));

		if (comprobante.getFechaEmision() == null) {
			comprobante.setFechaEmision(LocalDateTime.now());
		}
		if (comprobante.getEstadoComprobante() == null || comprobante.getEstadoComprobante().isBlank()) {
			comprobante.setEstadoComprobante("EMITIDO");
		}
		if (!List.of("BOLETA", "FACTURA").contains(comprobante.getTipo())) {
			throw new IllegalArgumentException("El tipo de comprobante no es valido.");
		}
		if (!List.of("EMITIDO", "ANULADO").contains(comprobante.getEstadoComprobante())) {
			throw new IllegalArgumentException("El estado del comprobante no es valido.");
		}

		double total = redondear(pedido.getTotal());
		double subtotal = redondear(total / 1.18);
		double igv = redondear(total - subtotal);

		comprobante.setSubtotal(subtotal);
		comprobante.setIgv(igv);
		comprobante.setTotal(total);
		comprobante.setPedido(pedido);
		comprobante.setUsuario(usuario);
	}

	private double redondear(double valor) {
		return Math.round(valor * 100.0) / 100.0;
	}

	private void procesarPagoPedido(Pedido pedido) {
		if ("PAGADO".equalsIgnoreCase(pedido.getEstadoPedido())) {
			throw new IllegalArgumentException("El pedido ya se encuentra pagado.");
		}
		if (pedido.getListaDetalles() == null || pedido.getListaDetalles().isEmpty()) {
			throw new IllegalArgumentException("El pedido no contiene platos.");
		}

		Map<Integer, Integer> cantidadesPorPlato = new HashMap<>();

		for (DetallePedido detalle : pedido.getListaDetalles()) {
			if (detalle.getPlato() == null || detalle.getPlato().getIdPlato() == null) {
				throw new IllegalArgumentException("El pedido contiene un plato no valido.");
			}

			cantidadesPorPlato.merge(
					detalle.getPlato().getIdPlato(),
					detalle.getCantidad(),
					Integer::sum);
		}

		Map<Integer, Plato> platos = new HashMap<>();

		for (Map.Entry<Integer, Integer> item : cantidadesPorPlato.entrySet()) {
			Plato plato = platoRepository.findById(item.getKey())
					.orElseThrow(() -> new IllegalArgumentException("Uno de los platos del pedido no existe."));

			if (plato.getStock() == null || plato.getStock() < item.getValue()) {
				throw new IllegalArgumentException(
						"Stock insuficiente para el plato " + plato.getNombre() + ".");
			}

			platos.put(item.getKey(), plato);
		}

		for (Map.Entry<Integer, Integer> item : cantidadesPorPlato.entrySet()) {
			Plato plato = platos.get(item.getKey());
			plato.setStock(plato.getStock() - item.getValue());
			platoRepository.save(plato);
		}

		pedido.setEstadoPedido("PAGADO");
		pedidoRepository.save(pedido);
	}
}
