package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.restaurante.entity.Comprobante;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {

    @Query("SELECT SUM(c.total) FROM Comprobante c WHERE c.estadoComprobante = 'ACTIVO'")
    Double obtenerVentasTotales();
}