package com.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurante.service.UsuarioService;

@Controller
@RequestMapping("/gestionusuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	

	@GetMapping("/lista")
	public String listandoUsuarios(Model modelito){
		
		modelito.addAttribute("mensaje", "Bienvenido al modulo de Usuarios");
		modelito.addAttribute("usuarios", usuarioService.listar());
		return "usuario/listaUsuarios";
	}
	
}
