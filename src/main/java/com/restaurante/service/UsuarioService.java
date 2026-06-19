package com.restaurante.service;

import java.util.List;

import com.restaurante.entity.Usuario;

public interface UsuarioService {

	List<Usuario> listar();
    Usuario login(String username, String password);

}