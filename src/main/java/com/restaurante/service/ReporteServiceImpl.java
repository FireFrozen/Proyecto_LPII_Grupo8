package com.restaurante.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurante.repository.ComprobanteRepository;
import com.restaurante.repository.PedidoRepository;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ComprobanteRepository comprobanteRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Override
    public Map<String, Object> obtenerMetricasDashboard() {
        Map<String, Object> metricas = new HashMap<>();

        // 1. Ventas Totales
        Double ventasTotales = comprobanteRepo.obtenerVentasTotales();
        metricas.put("ventasTotales", ventasTotales != null ? ventasTotales : 0.0);

        // 2. Plato más vendido
        List<Object[]> topPlato = pedidoRepo.obtenerPlatoMasVendido();
        if (!topPlato.isEmpty()) {
            metricas.put("platoNombre", topPlato.get(0)[0]);
            metricas.put("platoCantidad", topPlato.get(0)[1]);
        } else {
            metricas.put("platoNombre", "Sin datos");
            metricas.put("platoCantidad", 0);
        }

        // 3. Mesa más solicitada
        List<Object[]> topMesa = pedidoRepo.obtenerMesaMasSolicitada();
        if (!topMesa.isEmpty()) {
            metricas.put("mesaNumero", topMesa.get(0)[0]);
            metricas.put("mesaContador", topMesa.get(0)[1]);
        } else {
            metricas.put("mesaNumero", "N/A");
            metricas.put("mesaContador", 0);
        }

        return metricas;
    }
}
