package com.administrativo.ventas_service.Controller;

import com.administrativo.ventas_service.Dto.DetalleVentaDTO;
import com.administrativo.ventas_service.Service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleVentaController {

    @Autowired
    private  DetalleVentaService detalleVentaService;

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<DetalleVentaDTO>> listarDetallesPorVenta(@PathVariable Integer idVenta) {
        return ResponseEntity.ok(detalleVentaService.listarDetallesPorVenta(idVenta));
    }

}
