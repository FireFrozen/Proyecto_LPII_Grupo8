package com.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restaurante.entity.Plato;
import com.restaurante.service.CategoriaPlatoService;
import com.restaurante.service.PlatoService;

@Controller
@RequestMapping("/gestionplato")
public class PlatoController {

	@Autowired
	private PlatoService platoService;

	@Autowired
	private CategoriaPlatoService categoriaPlatoService;

	@GetMapping("/lista")
	public String listandoPlatos(Model modelito){

		modelito.addAttribute("mensaje", "Bienvenido al modulo de Platos");
		modelito.addAttribute("platos", platoService.listar());
		modelito.addAttribute("categorias", categoriaPlatoService.listar());
		return "plato/listaPlatos";
	}

	@PostMapping("/guardar")
	public String guardarPlato(Plato plato){
		platoService.guardar(plato);
		return "redirect:/gestionplato/lista";
	}

	@GetMapping("/cargar/{id}")
	@ResponseBody
	public Plato cargarPlato(@PathVariable("id") Integer id){
		return platoService.obtenerPorId(id);
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarPlato(@PathVariable("id") Integer id){
		platoService.eliminar(id);
		return "redirect:/gestionplato/lista";
	}
}
