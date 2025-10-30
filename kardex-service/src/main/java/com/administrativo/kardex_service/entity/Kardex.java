package com.administrativo.kardex_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

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
    @Column(name = "id_movimiento")
    private Integer idMovimiento;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    private Integer cantidad;

    @Column(name = "stock_anterior")
    private Integer stockAnterior;

    @Column(name = "stock_nuevo")
    private Integer stockNuevo;

    // 👇 CORRECTO: el nombre real de la columna es "fecha"
    @Column(name = "fecha", insertable = false, updatable = false)
    private Timestamp fecha;

    @Column(name = "referencia_id")
    private Integer referenciaId;

    // ⚠️ Opcional: si tu tabla tiene id_usuario
    @Column(name = "id_usuario")
    private Integer idUsuario;
}
