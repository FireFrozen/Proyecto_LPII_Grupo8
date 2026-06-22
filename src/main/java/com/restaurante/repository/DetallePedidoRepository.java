package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.entity.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

}
