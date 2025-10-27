package com.administrativo.ventas_service.Controller;

import com.administrativo.ventas_service.Dto.VentaRequestDTO;
import com.administrativo.ventas_service.Dto.VentaResponse;
import com.administrativo.ventas_service.Entity.Venta;
import com.administrativo.ventas_service.Service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    ResponseEntity<VentaResponse> guardarVenta(@RequestBody VentaRequestDTO ventaRequestDTO){
        VentaResponse ventaResponse = ventaService.crearVenta(ventaRequestDTO);
        return ResponseEntity.ok(ventaResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerVenta(id));
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        return ResponseEntity.ok(ventaService.listarVentas());
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarComprobante(@PathVariable Venta venta) throws Exception {
        byte[] pdf = ventaService.generarComprobante(venta);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprobante_" + venta + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
