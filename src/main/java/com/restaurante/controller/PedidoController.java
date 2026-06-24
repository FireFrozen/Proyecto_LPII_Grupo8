package com.restaurante.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurante.dto.PedidoRegistroForm;
import com.restaurante.entity.Cliente;
import com.restaurante.entity.Mesa;
import com.restaurante.entity.Pedido;
import com.restaurante.entity.Plato;
import com.restaurante.entity.Usuario;
import com.restaurante.service.ClienteService;
import com.restaurante.service.MesaService;
import com.restaurante.service.PedidoService;
import com.restaurante.service.PlatoService;
import com.restaurante.service.UsuarioService;

@Controller
@RequestMapping("/gestionpedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private MesaService mesaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PlatoService platoService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("mensaje", "Bienvenido al modulo de gestion de pedidos");
		model.addAttribute("pedidos", pedidoService.listar());
		return "pedido/mantPedidos";
	}

	@GetMapping("/nuevo")
	public String nuevoPedido(Model model) {
		List<Cliente> clientesActivos = clienteService.listar().stream()
				.filter(c -> "ACTIVO".equalsIgnoreCase(c.getEstadoRegistro()))
				.collect(Collectors.toList());

		List<Plato> platosActivos = platoService.listar().stream()
				.filter(p -> "ACTIVO".equalsIgnoreCase(p.getEstadoRegistro()))
				.collect(Collectors.toList());

		List<Mesa> mesasDisponibles = mesaService.listar().stream()
				.filter(m -> "ACTIVO".equalsIgnoreCase(m.getEstadoRegistro()))
				.filter(m -> "DISPONIBLE".equalsIgnoreCase(m.getEstadoMesa()))
				.collect(Collectors.toList());

		List<Usuario> usuariosActivos = usuarioService.listar().stream()
				.filter(u -> "ACTIVO".equalsIgnoreCase(u.getEstadoRegistro()))
				.collect(Collectors.toList());

		model.addAttribute("pedidoForm", new PedidoRegistroForm());
		model.addAttribute("clientes", clientesActivos);
		model.addAttribute("platos", platosActivos);
		model.addAttribute("mesas", mesasDisponibles);
		model.addAttribute("usuarios", usuariosActivos);
		return "pedido/registrarPedido";
	}

	@GetMapping("/detalle/{id}")
	public String mostrarDetallePedido(
			@PathVariable("id") int id,
			Model model,
			RedirectAttributes redirect) {
		Pedido pedido = pedidoService.buscarPorIdConDetalles(id);

		if (pedido == null) {
			redirect.addFlashAttribute("mensajeError", "El pedido solicitado no existe.");
			return "redirect:/gestionpedido/lista";
		}

		model.addAttribute("pedido", pedido);
		return "pedido/pedidoDetalles";
	}

	@PostMapping("/guardar")
	public String guardarPedido(
			@ModelAttribute("pedidoForm") PedidoRegistroForm pedidoForm,
			RedirectAttributes redirect) {
		try {
			pedidoService.registrarPedido(pedidoForm);
			redirect.addFlashAttribute("mensajeExito", "Pedido registrado correctamente.");
			return "redirect:/gestionpedido/lista";
		} catch (Exception e) {
			redirect.addFlashAttribute("mensajeError", "No se pudo registrar el pedido.");
			return "redirect:/gestionpedido/nuevo";
		}
	}

	@PostMapping("/actualizar-estado")
	public String actualizarEstadoPedido(
			@RequestParam("idPedido") int idPedido,
			@RequestParam("estadoPedido") String estadoPedido,
			RedirectAttributes redirect) {
		boolean actualizado = pedidoService.actualizarEstadoPedido(idPedido, estadoPedido);

		if (actualizado) {
			redirect.addFlashAttribute("mensajeExito", "Estado del pedido actualizado correctamente.");
		} else {
			redirect.addFlashAttribute("mensajeError", "No se pudo actualizar el estado del pedido.");
		}

		return "redirect:/gestionpedido/lista";
	}
}
