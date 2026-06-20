package com.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restaurante.entity.Mesa;
import com.restaurante.service.MesaService;

@Controller
@RequestMapping("/gestionmesa")
public class MesaController {

	@Autowired
	private MesaService mesaService;

	@GetMapping("/lista")
	public String listandoMesas(Model modelito){

		modelito.addAttribute("mensaje", "Bienvenido al modulo de Mesas");
		modelito.addAttribute("mesas", mesaService.listar());
		return "mesa/listaMesas";
	}

	@PostMapping("/guardar")
	public String guardarMesa(Mesa mesa){
		mesaService.guardar(mesa);
		return "redirect:/gestionmesa/lista";
	}

	@GetMapping("/cargar/{id}")
	@ResponseBody
	public Mesa cargarMesa(@PathVariable("id") Integer id){
		return mesaService.obtenerPorId(id);
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarMesa(@PathVariable("id") Integer id){
		mesaService.eliminar(id);
		return "redirect:/gestionmesa/lista";
	}
}
