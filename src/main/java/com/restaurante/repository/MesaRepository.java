package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurante.entity.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer>{

}
