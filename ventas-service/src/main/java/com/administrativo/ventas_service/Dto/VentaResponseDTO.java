package com.administrativo.ventas_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponseDTO {

    private Integer idVenta;
    private Integer idCliente;
    private Integer idUsuario;
    private LocalDateTime fechaVenta;
    private BigDecimal totalVenta;
    private String metodoPago;
    private String estado;
    private List<DetalleVentaResponseDTO> detalles;
}
