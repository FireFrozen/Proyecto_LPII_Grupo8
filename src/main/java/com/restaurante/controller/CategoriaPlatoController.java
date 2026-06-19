package com.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurante.service.CategoriaPlatoService;

@Controller
@RequestMapping("/gestioncategoria")
public class CategoriaPlatoController {
	
	@Autowired
	private CategoriaPlatoService CPService;
	

	@GetMapping("/lista")
	public String listandoCategorias(Model modelito){
		
		modelito.addAttribute("mensaje", "Bienvenido al modulo de Categorias de Platos");
		modelito.addAttribute("categorias", CPService.listar());
		return "categoriaplato/listaCategorias";
	}
	
}
