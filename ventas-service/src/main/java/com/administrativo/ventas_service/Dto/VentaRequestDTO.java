package com.administrativo.ventas_service.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VentaRequestDTO {
    private Integer idCliente;
    private Integer idUsuario;
    private BigDecimal totalVenta;
    private String metodoPago;
    private List<DetalleVentaRequestDTO> detalles;
}
