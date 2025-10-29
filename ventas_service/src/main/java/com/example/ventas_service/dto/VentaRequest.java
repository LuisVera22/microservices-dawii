package com.example.ventas_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRequest {
    private String documento; // DNI o RUC
    private List<DetalleVentaRequest> detalles;
}