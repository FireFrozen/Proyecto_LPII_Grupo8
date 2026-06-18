package com.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.entity.Rol;
import com.restaurante.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	RolRepository rolRepo;
	
	@Override
	public List<Rol> listar() {
		
		return rolRepo.findAll();
	}

}
