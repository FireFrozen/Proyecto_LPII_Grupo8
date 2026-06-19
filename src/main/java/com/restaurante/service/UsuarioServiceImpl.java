package com.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.entity.Usuario;
import com.restaurante.repository.UsuarioRepository;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
    private UsuarioRepository usuarioRepo;
	
	@Override
	public List<Usuario> listar() {
		
		return usuarioRepo.findAll();
	}

	@Override
	public Usuario login(String username, String password) {
		Optional<Usuario> usuario =
				usuarioRepo.findByUsernameAndPassword(username, password);

        return usuario.orElse(null);
	}

}
