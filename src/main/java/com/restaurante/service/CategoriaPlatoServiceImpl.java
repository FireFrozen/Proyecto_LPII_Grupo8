package com.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.entity.CategoriaPlato;
import com.restaurante.repository.CategoriaPlatoRepository;

@Service
public class CategoriaPlatoServiceImpl implements CategoriaPlatoService {

	@Autowired
	CategoriaPlatoRepository categoriaPlatoRepo;
	
	@Override
	public List<CategoriaPlato> listar() {
		
		return categoriaPlatoRepo.findAll();
	}

}
