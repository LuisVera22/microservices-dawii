package com.administrativo.kardex_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_movimiento;

    private Integer idProducto;
    private String tipoMovimiento;
    private Integer cantidad;
    private Integer stockAnterior;
    private Integer stockNuevo;

    @Column(
            name = "fechaMovimiento",
            columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false
    )
    private LocalDateTime fechaMovimiento;

    private Integer referenciaId; // por ejemplo: id de la venta
}
