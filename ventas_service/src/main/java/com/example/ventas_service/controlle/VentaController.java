package com.example.ventas_service.controlle;

import com.example.ventas_service.dto.VentaRequest;
import com.example.ventas_service.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearVenta(@RequestBody VentaRequest request) {
        return ResponseEntity.ok(ventaService.crearVenta(request));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodasLasVentas() {
        return ResponseEntity.ok(ventaService.listarTodasLasVentas());
    }

    @GetMapping("/boletas")
    public ResponseEntity<Map<String, Object>> listarBoletas() {
        return ResponseEntity.ok(ventaService.listarBoletas());
    }

    @GetMapping("/facturas")
    public ResponseEntity<Map<String, Object>> listarFacturas() {
        return ResponseEntity.ok(ventaService.listarFacturas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generarPdf(@PathVariable Long id) {
        byte[] pdf = ventaService.generarPdf(id);

        if (pdf == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "comprobante-" + id + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}