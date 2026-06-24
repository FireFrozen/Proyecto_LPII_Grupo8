package com.restaurante.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.restaurante.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Obtiene el nombre del plato más vendido y su cantidad total
    @Query("SELECT dp.plato.nombre, SUM(dp.cantidad) FROM DetallePedido dp " +
           "GROUP BY dp.plato.nombre " +
           "ORDER BY SUM(dp.cantidad) DESC")
    List<Object[]> obtenerPlatoMasVendido();

    // Obtiene el número de mesa más solicitado
    @Query("SELECT p.mesa.numeroMesa, COUNT(p) FROM Pedido p " +
           "WHERE p.mesa IS NOT NULL " +
           "GROUP BY p.mesa.numeroMesa " +
           "ORDER BY COUNT(p) DESC")
    List<Object[]> obtenerMesaMasSolicitada();
}