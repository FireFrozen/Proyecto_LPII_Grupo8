package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
