package com.example.ventas_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "cliente_nombres", nullable = false)
    private String clienteNombres;

    @Column(name = "cliente_documento", nullable = false)
    private String clienteDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comprobante", nullable = false)
    private TipoComprobante tipoComprobante;

    @Column(name = "numero_comprobante", nullable = false, unique = true)
    private String numeroComprobante;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double igv;

    @Column(nullable = false)
    private Double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleVenta> detalles = new ArrayList<>();

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @PrePersist
    protected void onCreate() {
        fechaVenta = LocalDateTime.now();
    }

    public enum TipoComprobante {
        BOLETA, FACTURA
    }
}
