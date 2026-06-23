package com.restaurante.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurante.entity.Cliente;
import com.restaurante.entity.DetallePedido;
import com.restaurante.entity.Mesa;
import com.restaurante.entity.Pedido;
import com.restaurante.entity.Plato;
import com.restaurante.entity.Usuario;
import com.restaurante.service.ClienteService;
import com.restaurante.service.MesaService;
import com.restaurante.service.PedidoService;
import com.restaurante.service.PlatoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private MesaService mesaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PlatoService platoService;

	@GetMapping("/nuevo")
	public String nuevoPedido(Model modelito){

		List<Cliente> clientesActivos = clienteService.listar().stream()
				.filter(c -> "ACTIVO".equals(c.getEstadoRegistro()))
				.collect(Collectors.toList());

		List<Plato> platosActivos = platoService.listar().stream()
				.filter(p -> "ACTIVO".equals(p.getEstadoRegistro()))
				.collect(Collectors.toList());

		List<Mesa> mesasDisponibles = mesaService.listar().stream()
				.filter(m -> "DISPONIBLE".equals(m.getEstadoMesa()))
				.collect(Collectors.toList());

		modelito.addAttribute("clientes", clientesActivos);
		modelito.addAttribute("platos", platosActivos);
		modelito.addAttribute("mesas", mesasDisponibles);
		return "pedido/registrarPedido";
	}

	@PostMapping("/guardar")
	public String guardarPedido(@ModelAttribute("pedido") Pedido pedido) {
	    try {
	        // 1. Crear un usuario temporal con ID 1 para saltar la restricción de la BD
	        com.restaurante.entity.Usuario usuarioFicticio = new com.restaurante.entity.Usuario();
	        usuarioFicticio.setIdUsuario(1); // <- Si el campo en tu entidad se llama id_usuario, cámbialo aquí
	        
	        // 2. Vincular el usuario al pedido
	        pedido.setUsuario(usuarioFicticio);
	        
	        // 3. Establecer la fecha actual del sistema si llega vacía
	        if (pedido.getFechaHora() == null) {
	            pedido.setFechaHora(java.time.LocalDateTime.now());
	        }
	        
	        // 4. Registrar el pedido usando el servicio de tu compañero
	        pedidoService.registrarPedido(pedido);
	        
	        return "redirect:/pedido/nuevo?exito";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/pedido/nuevo?error";
	    }
	}
}
