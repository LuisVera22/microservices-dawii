package com.example.ventas_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stockActual;

}