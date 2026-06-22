package com.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurante.entity.Cliente;
import com.restaurante.service.ClienteService;

@Controller
@RequestMapping("/gestioncliente")
public class ClienteController {



	@Autowired
	private ClienteService clienteService;

	// LISTAR CLIENTES

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("mensaje","Bienvenido al módulo de gestión de clientes");
		model.addAttribute("clientes",clienteService.listar());
		// Objeto vacío para el formulario
		model.addAttribute("cliente",new Cliente());
		return "cliente/mantClientes";
	}


	// REGISTRAR CLIENTE

	@PostMapping("/registrar")
	public String registrar(Cliente cliente,RedirectAttributes redirect) {
		clienteService.registrarCliente(cliente);
		redirect.addFlashAttribute("mensajeExito","Cliente registrado correctamente");
		return "redirect:/gestioncliente/lista";
	}

		// BUSCAR POR ID
    @GetMapping("/buscar/{id}")
	@ResponseBody
	public Cliente buscarPorId(@PathVariable int id) {
		return clienteService.buscarPorId(id);
	}

	// ACTUALIZAR CLIENTE
	@PostMapping("/actualizar")
	public String actualizar(Cliente cliente,RedirectAttributes redirect) {
		clienteService.actualizarCliente(cliente);
		redirect.addFlashAttribute("mensajeExito","Cliente actualizado correctamente");
		return "redirect:/gestioncliente/lista";
	}

	// DESACTIVAR CLIENTE
	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable int id,RedirectAttributes redirect) {
		clienteService.desactivarEstadoCliente(id);
		redirect.addFlashAttribute("mensajeExito","Cliente desactivado correctamente");
		return "redirect:/gestioncliente/lista";
	}

}