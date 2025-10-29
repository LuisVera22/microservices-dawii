package com.example.ventas_service.dto;


import com.example.ventas_service.entity.Venta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponse {
    private Long id;
    private Long clienteId;
    private String clienteNombres;
    private String clienteDocumento;
    private Venta.TipoComprobante tipoComprobante;
    private String numeroComprobante;
    private List<DetalleVentaResponse> detalles;
    private Double subtotal;
    private Double igv;
    private Double total;
    private LocalDateTime fechaVenta;
}