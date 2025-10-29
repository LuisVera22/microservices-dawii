package com.example.ventas_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KardexClientService {

    private final RestTemplate restTemplate;

    // 🔹 URL base del kardex-service (asegúrate que sea el puerto correcto)
    @Value("${kardex.service.url:http://localhost:8001/kardex}")
    private String kardexServiceUrl;

    // 🔹 Registrar movimiento en el kardex
    public void registrarMovimiento(Integer idProducto, String tipo, Integer cantidad,
                                    Integer stockAnterior, Integer stockNuevo,
                                    Integer referenciaId) {

        String url = kardexServiceUrl; // ✅ POST directo a /kardex
        Map<String, Object> body = new HashMap<>();
        body.put("idProducto", idProducto);
        body.put("tipoMovimiento", tipo);
        body.put("cantidad", cantidad);
        body.put("stockAnterior", stockAnterior);
        body.put("stockNuevo", stockNuevo);
        body.put("referenciaId", referenciaId);
        body.put("fechaMovimiento", java.time.LocalDateTime.now().toString()); // opcional

        restTemplate.postForObject(url, body, Void.class);
    }

    // 🔹 Registrar salida de producto
    public void registrarSalida(Integer idProducto, Integer cantidad,
                                Integer stockAnterior, Integer stockNuevo,
                                Integer referenciaId) {
        registrarMovimiento(idProducto, "SALIDA", cantidad, stockAnterior, stockNuevo, referenciaId);
    }
}
