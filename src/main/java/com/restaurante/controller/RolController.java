package com.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurante.service.RolService;

@Controller
@RequestMapping("/gestionrol")
public class RolController {
	
	@Autowired
	private RolService rolService;
	

	@GetMapping("/lista")
	public String listandoCiudades(Model modelito){
		
		modelito.addAttribute("mensaje", "Bienvenido al modulo de Roles");
		modelito.addAttribute("roles", rolService.listar());
		return "rol/listaRoles";
	}
	
}
