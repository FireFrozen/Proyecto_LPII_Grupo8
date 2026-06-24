package com.restaurante.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.dto.DetallePedidoRegistroForm;
import com.restaurante.dto.PedidoRegistroForm;
import com.restaurante.entity.Cliente;
import com.restaurante.entity.DetallePedido;
import com.restaurante.entity.Mesa;
import com.restaurante.entity.Pedido;
import com.restaurante.entity.Plato;
import com.restaurante.entity.Usuario;
import com.restaurante.repository.ClienteRepository;
import com.restaurante.repository.MesaRepository;
import com.restaurante.repository.PedidoRepository;
import com.restaurante.repository.PlatoRepository;
import com.restaurante.repository.UsuarioRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository repoPedido;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private MesaRepository mesaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PlatoRepository platoRepository;

	@Override
	public List<Pedido> listar() {
		return repoPedido.findAll();
	}

	@Override
	public List<Pedido> listarNoPagados() {
		return repoPedido.findByEstadoPedidoNot("PAGADO");
	}

	@Override
	@Transactional
	public Pedido registrarPedido(PedidoRegistroForm pedidoForm) {
		validarFormulario(pedidoForm);

		Cliente cliente = clienteRepository.findById(pedidoForm.getIdCliente())
				.orElseThrow(() -> new IllegalArgumentException("El cliente seleccionado no existe."));
		Mesa mesa = mesaRepository.findById(pedidoForm.getIdMesa())
				.orElseThrow(() -> new IllegalArgumentException("La mesa seleccionada no existe."));
		Usuario usuario = usuarioRepository.findById(pedidoForm.getIdUsuario())
				.orElseThrow(() -> new IllegalArgumentException("El usuario seleccionado no existe."));

		validarEntidadesActivas(cliente, mesa, usuario);

		Pedido pedido = new Pedido();
		pedido.setFechaHora(LocalDateTime.now());
		pedido.setEstadoPedido("PENDIENTE");
		pedido.setCliente(cliente);
		pedido.setMesa(mesa);
		pedido.setUsuario(usuario);

		List<DetallePedido> detalles = new ArrayList<>();
		double total = 0;

		for (DetallePedidoRegistroForm detalleForm : pedidoForm.getDetalles()) {
			if (detalleForm == null || detalleForm.getIdPlato() == null
					|| detalleForm.getCantidad() == null || detalleForm.getCantidad() <= 0) {
				throw new IllegalArgumentException("Todos los platos deben tener una cantidad valida.");
			}

			Plato plato = platoRepository.findById(detalleForm.getIdPlato())
					.orElseThrow(() -> new IllegalArgumentException("Uno de los platos seleccionados no existe."));

			if (!"ACTIVO".equalsIgnoreCase(plato.getEstadoRegistro())) {
				throw new IllegalArgumentException("Uno de los platos seleccionados ya no esta activo.");
			}

			double precioUnitario = plato.getPrecio();
			double subtotal = precioUnitario * detalleForm.getCantidad();

			DetallePedido detalle = new DetallePedido();
			detalle.setCantidad(detalleForm.getCantidad());
			detalle.setPrecioUnitario(precioUnitario);
			detalle.setSubtotal(subtotal);
			detalle.setPlato(plato);
			detalle.setPedido(pedido);
			detalles.add(detalle);
			total += subtotal;
		}

		pedido.setListaDetalles(detalles);
		pedido.setTotal(total);
		return repoPedido.save(pedido);
	}

	private void validarFormulario(PedidoRegistroForm pedidoForm) {
		if (pedidoForm == null || pedidoForm.getIdCliente() == null
				|| pedidoForm.getIdMesa() == null || pedidoForm.getIdUsuario() == null) {
			throw new IllegalArgumentException("Cliente, mesa y usuario son obligatorios.");
		}
		if (pedidoForm.getDetalles() == null || pedidoForm.getDetalles().isEmpty()) {
			throw new IllegalArgumentException("Agregue al menos un plato al pedido.");
		}
	}

	private void validarEntidadesActivas(Cliente cliente, Mesa mesa, Usuario usuario) {
		if (!"ACTIVO".equalsIgnoreCase(cliente.getEstadoRegistro())) {
			throw new IllegalArgumentException("El cliente seleccionado no esta activo.");
		}
		if (!"ACTIVO".equalsIgnoreCase(mesa.getEstadoRegistro())
				|| !"DISPONIBLE".equalsIgnoreCase(mesa.getEstadoMesa())) {
			throw new IllegalArgumentException("La mesa seleccionada ya no esta disponible.");
		}
		if (!"ACTIVO".equalsIgnoreCase(usuario.getEstadoRegistro())) {
			throw new IllegalArgumentException("El usuario seleccionado no esta activo.");
		}
	}

	@Override
	public Pedido buscarPorId(int id) {
		return repoPedido.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido buscarPorIdConDetalles(int id) {
		Pedido pedido = repoPedido.findById(id).orElse(null);

		if (pedido != null) {
			pedido.getListaDetalles().size();
			pedido.getListaDetalles().forEach(detalle -> detalle.getPlato().getNombre());
		}

		return pedido;
	}

	@Override
	public void actualizarPedido(Pedido pedido) {
		repoPedido.save(pedido);
	}

	@Override
	@Transactional
	public boolean actualizarEstadoPedido(int id, String estadoPedido) {
		List<String> estadosPermitidos = List.of(
				"PENDIENTE",
				"EN_PREPARACION",
				"ENTREGADO",
				"PAGADO",
				"ANULADO");

		if (!estadosPermitidos.contains(estadoPedido)) {
			return false;
		}

		Pedido pedido = repoPedido.findById(id).orElse(null);
		if (pedido == null) {
			return false;
		}

		pedido.setEstadoPedido(estadoPedido);
		repoPedido.save(pedido);
		return true;
	}

	@Override
	public void desactivarEstadoPedido(int id) {
		Pedido pedido = buscarPorId(id);
		if (pedido != null) {
			pedido.setEstadoPedido("ANULADO");
			repoPedido.save(pedido);
		}
	}
}
