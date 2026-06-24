package com.restaurante.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurante.entity.Comprobante;
import com.restaurante.entity.Pedido;
import com.restaurante.entity.Usuario;
import com.restaurante.service.ComprobanteService;
import com.restaurante.service.PedidoService;
import com.restaurante.service.UsuarioService;

@Controller
@RequestMapping("/gestioncomprobante")
public class ComprobanteController {

	@Autowired
	private ComprobanteService comprobanteService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/lista")
	public String listar(Model model) {
		List<Pedido> pedidosNoPagados = pedidoService.listarNoPagados();
		List<Pedido> pedidosTodos = pedidoService.listar();
		List<Integer> idsPedidosNoPagados = pedidosNoPagados.stream()
				.map(Pedido::getIdPedido)
				.collect(Collectors.toList());
		List<Usuario> cajeros = usuarioService.listar().stream()
				.filter(u -> "ACTIVO".equalsIgnoreCase(u.getEstadoRegistro()))
				.filter(u -> u.getRol() != null && "CAJERO".equalsIgnoreCase(u.getRol().getNombre()))
				.collect(Collectors.toList());

		model.addAttribute("mensaje", "Bienvenido al modulo de gestion de comprobantes");
		model.addAttribute("comprobantes", comprobanteService.listar());
		model.addAttribute("pedidosTodos", pedidosTodos);
		model.addAttribute("idsPedidosNoPagados", idsPedidosNoPagados);
		model.addAttribute("cajeros", cajeros);
		model.addAttribute("comprobante", new Comprobante());
		return "comprobante/mantComprobantes";
	}

	@PostMapping("/registrar")
	public String registrar(Comprobante comprobante, RedirectAttributes redirect) {
		try {
			comprobanteService.registrarComprobante(comprobante);
			redirect.addFlashAttribute("mensajeExito", "Comprobante registrado correctamente.");
		} catch (Exception e) {
			redirect.addFlashAttribute("mensajeError", "No se pudo registrar el comprobante.");
		}
		return "redirect:/gestioncomprobante/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Comprobante buscarPorId(@PathVariable int id) {
		return comprobanteService.buscarPorId(id);
	}

	@PostMapping("/actualizar")
	public String actualizar(Comprobante comprobante, RedirectAttributes redirect) {
		try {
			comprobanteService.actualizarComprobante(comprobante);
			redirect.addFlashAttribute("mensajeExito", "Comprobante actualizado correctamente.");
		} catch (Exception e) {
			redirect.addFlashAttribute("mensajeError", "No se pudo actualizar el comprobante.");
		}
		return "redirect:/gestioncomprobante/lista";
	}

	@GetMapping("/anular/{id}")
	public String anular(@PathVariable int id, RedirectAttributes redirect) {
		comprobanteService.desactivarEstadoComprobante(id);
		redirect.addFlashAttribute("mensajeExito", "Comprobante anulado correctamente.");
		return "redirect:/gestioncomprobante/lista";
	}
}
