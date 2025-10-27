package com.administrativo.ventas_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponse {
    private Integer ventaId;
    private LocalDateTime fecha;
    private BigDecimal totalVenta;
    private String message;
}
