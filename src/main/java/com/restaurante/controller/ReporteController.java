package com.restaurante.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.restaurante.service.ReporteService;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/dashboard")
    public String verDashboardReportes(Model model) {
        Map<String, Object> datosMetricas = reporteService.obtenerMetricasDashboard();
        model.addAttribute("metricas", datosMetricas);
        return "reportesDashboard";
    }
}