package com.administrativo.ventas_service.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoDTO {

    private Integer idProducto;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
}
