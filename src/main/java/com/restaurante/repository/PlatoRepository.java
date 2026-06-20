package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurante.entity.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer>{

}
