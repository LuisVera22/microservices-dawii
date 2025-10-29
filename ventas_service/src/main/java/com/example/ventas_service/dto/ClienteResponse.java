package com.example.ventas_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String ruc;
    private String tipoDocumento;
    private String direccion;
    private String telefono;
    private String email;

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public String getNumeroDocumento() {
        return dni != null ? dni : ruc;
    }
}