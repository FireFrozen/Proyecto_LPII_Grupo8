package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.entity.CategoriaPlato;

@Repository
public interface CategoriaPlatoRepository extends JpaRepository<CategoriaPlato, Integer>{

}
