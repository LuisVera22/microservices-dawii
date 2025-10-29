package com.example.ventas_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaRequest {
    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;
}