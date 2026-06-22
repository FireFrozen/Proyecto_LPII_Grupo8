package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.entity.Comprobante;

public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {

}
